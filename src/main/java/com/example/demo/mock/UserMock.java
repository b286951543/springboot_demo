package com.example.demo.mock;

import com.example.demo.model.User;

public class UserMock {

    public static String getPassword(String username) {
        System.out.println("用户名:   " + username);
        if(username.equals("admin")) {
            return "123";
        }else {
            return "456";
        }
    }

    public static String getRole(String username) {
        if(username.equals("admin")) {
            return "admin";
        }else {
            return "user";
        }
    }

    public static User getUserByUsername(String username) {
        User user = new User();
        if(username.equals("admin")) {
            user.setUsername("admin");
            user.setPassword("123");
            user.setRole("admin");
        }else {
            user.setUsername("admin1");
            user.setPassword("456");
            user.setRole("user");
        }
        user.setState("1");
        return user;
    }

}

