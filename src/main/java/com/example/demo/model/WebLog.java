package com.example.demo.model;

import lombok.Data;

@Data // 提供get set 方法
public class WebLog {
    /**
     * 请求参数
     */
    private Object params;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 操作时间
     */
    private Long startTime;

    /**
     * 消耗时间
     */
    private Integer spendTime;

    /**
     * 根路径
     */
    private String basePath;


    /**
     * 请求返回的结果
     */
    private Object response;

    /**
     * IP地址
     */
    private String ip;

}
