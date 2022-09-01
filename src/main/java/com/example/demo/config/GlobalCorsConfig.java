package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {

    /**
     * 允许跨域调用的过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //允许白名单域名进行跨域调用 Access-Control-Allow-Origin
        config.addAllowedOrigin("*");
        //允许跨越发送cookie Access-Control-Allow-Credentials
        config.setAllowCredentials(true);
        //放行全部原始头信息 Access-Control-Allow-Headers，具体有Origin,X-Requested-With,Content-Type,Accept,Authorization,token,cid,user_cookie,store,deviceid,imageVerificationCode
        config.addAllowedHeader("*");
        //允许所有请求方法跨域调用 Access-Control-Allow-Methods，具体有POST, GET, OPTIONS, DELETE, PUT
        config.addAllowedMethod("*");
        // 允许以这种方式实现跨域请求的最长时间, 3600秒之内不需要再发送预请求来验证了，发正式请求就可以了。
//        config.setMaxAge(3600L);
        // 用于前端获取请求头的 imageVerificationCode 信息
//        config.addExposedHeader("imageVerificationCode");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
