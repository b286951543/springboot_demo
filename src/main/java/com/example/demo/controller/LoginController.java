package com.example.demo.controller;

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
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 获取Subject对象
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);
        Subject subject = SecurityUtils.getSubject();
        try {
            // 执行登录
            subject.login(token);
            return "ok";
        } catch (UnknownAccountException e) {
            return e.getMessage();
        } catch (IncorrectCredentialsException e) {
            return "IncorrectCredentialsException " + e.getMessage();
        } catch (LockedAccountException e) {
            return "LockedAccountException " + e.getMessage();
        } catch (AuthenticationException e) {
            return "认证失败！";
        }
    }
}
