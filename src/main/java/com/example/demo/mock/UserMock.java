package com.example.demo.mock;

import com.example.demo.model.User;

public class UserMock {

    public static String getUserName(String userId) {
        if(userId.equals("1")) {
            return "admin";
        }else {
            return "user";
        }
    }

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

    public static Integer getId(String username) {
        if(username.equals("admin")) {
            return 1;
        }else {
            return 2;
        }
    }

    public static String getRoleById(String userId){
        if(userId.equals("1")) {
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
            user.setId(1);
        }else {
            user.setUsername("admin1");
            user.setPassword("456");
            user.setRole("user");
            user.setId(2);
        }
        user.setState("1");
        return user;
    }

    public static String getPermission(String username){
        if(username.equals("admin")) {
            return "p:admin";
        }else {
            return "p:admin";
        }
    }

}

