package com.example.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.example.demo.mapper"}) // 扫描该包下的接口，然后对这些接口进行实现，提供给外部调用。接口的实现其实是用我们的xxxMapper.xml进行定义的
public class MyBatisConfig {}
