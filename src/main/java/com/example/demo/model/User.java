package com.example.demo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    public Integer id;
    public String username;
    public String password;
    public String role;
    public String state;
}
