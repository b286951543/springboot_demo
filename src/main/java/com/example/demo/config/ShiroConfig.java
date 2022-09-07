package com.example.demo.config;

import cn.hutool.core.codec.Base64;
import com.example.demo.Shiro.CustomRealm;
//import com.example.demo.session.ShiroSessionListener;
import com.example.demo.filter.JwtFilter;
import com.example.demo.session.ShiroSessionListener;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
//import org.crazycake.shiro.RedisSessionDAO;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

// 鉴权框架 Shiro 配置
@Configuration
public class ShiroConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    private static final Integer expireAt = 1800;

    private static final Integer timeout = 3000;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 登录的url
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后跳转的url
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权url
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        // 添加 jwt 过滤器
        LinkedHashMap<String, Filter> filters = new LinkedHashMap<>();
        filters.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filters);

        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 	/admins/**=anon               # 表示该 uri 可以匿名访问
        //	/admins/**=authc              # 表示该 uri 需要认证才能访问，不包括remember me
        //	/admins/**=authcBasic         # 表示该 uri 需要 httpBasic 认证
        //	/admins/**=perms[user:add:*]  # 表示该 uri 需要认证用户拥有 user:add:* 权限才能访问
        //	/admins/**=port[8080]         # 表示该 uri 需要使用 8080 端口
        //	/admins/**=roles[admin]       # 表示该 uri 需要认证用户拥有 admin 角色才能访问
        //	/admins/**=ssl                # 表示该 uri 需要使用 https 协议
        //	/admins/**=user               # 表示该 uri 需要认证或通过记住我认证才能访问
        //	/logout=logout                # 表示注销,可以当作固定配置

        // 定义filterChain，静态资源不拦截
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");

        // druid数据源监控页面不拦截
        filterChainDefinitionMap.put("/druid/**", "anon");

        // 配置退出过滤器，其中具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout"); // 表示注销

        // 表示该 uri 可以匿名访问
//        filterChainDefinitionMap.put("/", "anon");
//        filterChainDefinitionMap.put("/index", "anon");
//        filterChainDefinitionMap.put("/login", "anon");

        // 除上以外所有url都必须认证通过才可以访问，未通过认证自动访问LoginUrl
//        filterChainDefinitionMap.put("/**", "authc"); // 用户认证通过才能访问

        // 这里配置的url没效果，不知道是不是
//        filterChainDefinitionMap.put("/index", "authc,roles[\"管理员\"]"); // 访问index时，需要登录，并且需要 管理员 角色
//        filterChainDefinitionMap.put("/index", "perms[\"p:user:index\"]"); // 访问index时，需要 p:user:index 权限，校验不通过则跳到403
//        filterChainDefinitionMap.put("/**", "user"); // user指的是用户认证通过或者配置了Remember Me记住用户登录状态后可访问

        // 所有请求都要经过 jwt过滤器
        filterChainDefinitionMap.put("/**", "jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        logger.warn("Shiro jwt 拦截器工厂类注入成功");

        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(){
        // 配置SecurityManager，并注入shiroRealm
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();

        // 设置realm
        securityManager.setRealm(shiroRealm());

        // 添加rememberManager
        securityManager.setRememberMeManager(rememberMeManager());

        // 添加缓存
        securityManager.setCacheManager(cacheManager());

        // 注入session manager
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 自定义实现的Realm
     * @return
     */
    @Bean
    public CustomRealm shiroRealm(){
        CustomRealm shiroRealm = new CustomRealm();
        return shiroRealm;
    }

    /**
     * 开启注解
     *
     * // 表示Subject已经通过login身份验证
     * @RequiresAuthentication
     *
     * // 表示当前需要用户身份
     * @RequiresUser
     *
     * // 表示当前需要游客身份。
     * @RequiresGuest
     *
     * // 表示当前需要的角色
     * @RequiresRoles(value={"admin", "user"}, logical= Logical.AND)
     *
     * // 表示当前需要的权限
     * @RequiresPermissions (value = { " p : admin ", " p : user " }, logical = Logical.OR)
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * cookie
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        // 设置cookie名称
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 过期时间，单位秒
        cookie.setMaxAge(86400);
        return cookie;
    }

    /**
     * cookie管理对象，每次重启都会重新生成对称加密密钥
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // cookie加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("6ZmI6I2j5Y+R5aSn5ZOlAA=="));
        return cookieRememberMeManager;
    }

    // 缓存
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisHost);
        redisManager.setPort(redisPort);
        redisManager.setPassword(redisPassword);
        redisManager.setExpire(expireAt);
        redisManager.setTimeout(timeout);
        return redisManager;
    }
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    // session 会话
    @Bean
    public RedisSessionDAO sessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean
    public SimpleCookie sessionIdCookie(){
        SimpleCookie cookie = new SimpleCookie("X-Token");
        cookie.setMaxAge(-1); // 无有效期
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        return cookie;
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        listeners.add(new ShiroSessionListener());

        // 会把sessionId 存入cookie里面
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }
}
