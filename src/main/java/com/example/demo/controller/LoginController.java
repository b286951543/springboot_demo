package com.example.demo.controller;

import com.example.demo.mock.UserMock;
import com.example.demo.model.User;
import com.example.demo.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

    /**
     * http://localhost:8080/api/login
     *
     * http://localhost:8080/dev-api/login?username=admin2&password=456
     *
     * <p>
     * 使用 form-data 来传递参数
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/login")
    @ResponseBody
    public String login(String username, String password) {
        //数据库查询
        User user = UserMock.getUserByUsername(username);

        //把userid附上,生成jwt返回给前台
        String sign = JwtUtil.sign(user);
        return sign;

        // 使用了 jwt 后，不能再使用下面的方式来登录了
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //  获取Subject对象
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);
//        Subject subject = SecurityUtils.getSubject();
//        try {
//            // 执行登录
//            subject.login(token);
//            return "ok";
//        } catch (UnknownAccountException e) {
//            return e.getMessage();
//        } catch (IncorrectCredentialsException e) {
//            return "IncorrectCredentialsException " + e.getMessage();
//        } catch (LockedAccountException e) {
//            return "LockedAccountException " + e.getMessage();
//        } catch (AuthenticationException e) {
//            return "认证失败！";
//        }
    }
}
