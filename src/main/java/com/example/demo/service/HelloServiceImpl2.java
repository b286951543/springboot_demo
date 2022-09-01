package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service("helloServiceImpl2")
public class HelloServiceImpl2 implements HelloService2 {
    @Override
    public void hello2() {
        System.out.println("HelloServiceImpl2");
    }
}
