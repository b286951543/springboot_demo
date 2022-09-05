package com.example.demo.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    // 捕获 Shiro 校验的异常
    @ExceptionHandler(value = AuthorizationException.class)
    public String handleAuthorizationException() {
        return "您当前没有权限访问~ 请联系管理员";
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    // 自定义浏览器返回状态码
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String error(Exception e) {
        return e.getMessage();
    }



}
