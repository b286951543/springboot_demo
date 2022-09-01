package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RequestMapping("/redis")
@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // http://localhost:8080/api/redis/set?value=2
    @GetMapping("/set")
    public String setData(@RequestParam String value) {
        String key = "data";
        // 可使用软件 Redis Desktop Manager 查看redis 的数据
        stringRedisTemplate.opsForValue().set(key, value);
        return "success";
    }

    @GetMapping("/expire")
    public String expireData() {
        String key = "data";
        if(!stringRedisTemplate.expire(key, 10, TimeUnit.SECONDS)) {
            return "fail";
        }
        return "success";
    }

    @GetMapping("/del")
    public String deleteData() {
        String key = "data";
        stringRedisTemplate.delete(key);
        return "success";
    }

    /**
     * 计数器，实现自增功能，可以用它做网站访问统计
     * @return
     */
    @GetMapping("/increment")
    public Long incrementData() {
        String key = "data";
        return stringRedisTemplate.opsForValue().increment(key,1);
    }
}
