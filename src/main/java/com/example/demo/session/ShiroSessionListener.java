//package com.example.demo.session;
//
//import org.apache.shiro.session.Session;
//import org.apache.shiro.session.SessionListener;
//
//import java.io.Serializable;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class ShiroSessionListener implements SessionListener, Serializable {
//    // 原子值，可以维护用户数
//    private final AtomicInteger sessionCount = new AtomicInteger(0);
//
//    // 建立时
//    @Override
//    public void onStart(Session session) {
//        sessionCount.incrementAndGet();
//    }
//
//    // 停止时
//    @Override
//    public void onStop(Session session) {
//        sessionCount.decrementAndGet();
//    }
//
//    // 过期时
//    @Override
//    public void onExpiration(Session session) {
//        sessionCount.decrementAndGet();
//    }
//}
//
