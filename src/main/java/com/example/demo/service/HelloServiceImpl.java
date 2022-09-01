package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service("helloServiceImpl")
public class HelloServiceImpl implements HelloService {
    @Override
    public void hello() {
        System.out.println("HelloServiceImpl");
    }
}
