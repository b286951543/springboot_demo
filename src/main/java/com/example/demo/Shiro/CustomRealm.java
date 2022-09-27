package com.example.demo.Shiro;

import com.example.demo.mock.UserMock;
import com.example.demo.model.JwtToken;
import com.example.demo.model.User;
import com.example.demo.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    /**
     * 使用 jwt 必须实现该方法
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }


    /**
     * 获取用户角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
//        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        String userName = user.getUsername();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        String userId = JwtUtil.getUserId(principal.toString());
        if(userId == null) {
            return simpleAuthorizationInfo;
        }

        String userName = UserMock.getUserName(userId);

        // 获取用户角色
        Set<String> roleSet = new HashSet<>();
        String role = UserMock.getRole(userName);
        roleSet.add(role);
        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限
        String permission = UserMock.getPermission(userName);
        Set<String> permissionSet = new HashSet<String>();
        permissionSet.add(permission);
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;



//        SysUser user = ShiroUtils.getSysUser();
//        // 角色列表
//        Set<String> roles = new HashSet<String>();
//        // 功能列表
//        Set<String> menus = new HashSet<String>();
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        // 管理员拥有所有权限
//        if (user.isAdmin())
//        {
//            info.addRole("admin");
//            info.addStringPermission("*:*:*");
//        }
//        else
//        {
//            roles = roleService.selectRoleKeys(user.getUserId());
//            menus = menuService.selectPermsByUserId(user.getUserId());
//            // 角色加入AuthorizationInfo认证对象
//            info.setRoles(roles);
//            // 权限加入AuthorizationInfo认证对象
//            info.setStringPermissions(menus);
//        }
//        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 使用jwt 后，使用该方式
        String token = (String) authenticationToken.getCredentials();
        String userId = JwtUtil.getUserId(token);
        if (StringUtils.isBlank(userId)) {
            throw new AuthenticationException("验证失败");
        }

        // 校验 userId 是否存在等写在这里
        String userRole = UserMock.getRoleById(userId);
        User userBean = new User();
        userBean.setId(Integer.valueOf(userId));
        userBean.setRole(userRole);
        if (!JwtUtil.verify(token, userBean)) {
            throw new AuthenticationException("token失效");
        }

        return new SimpleAuthenticationInfo(token, token, "shiroJwtRealm");

        // 不使用jwt,则使用该方式
        // 获取用户输入的用户名和密码
//        String userName = (String) authenticationToken.getPrincipal();
//        String password = new String((char[]) authenticationToken.getCredentials());
//
//        System.out.println("用户" + userName + "认证-----ShiroRealm.doGetAuthenticationInfo");
//
//        // 通过用户名到数据库查询用户信息
//        User user = UserMock.getUserByUsername(userName);
//        if (user == null) {
//            throw new UnknownAccountException("用户名或密码错误！");
//        }
//        if (!password.equals(user.getPassword())) {
//            throw new IncorrectCredentialsException("用户名或密码错误！");
//        }
//        if (user.getState().equals("0")) {
//            throw new LockedAccountException("账号已被锁定,请联系管理员！");
//        }
//
//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
//        return info;
    }
}
