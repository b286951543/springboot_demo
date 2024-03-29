package com.example.demo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserOnline implements Serializable {

    private static final long serialVersionUID = 3828664348416633856L;

    private String sessionId;

    private String userId;

    private String username;

    private String host;

    private String ip;

    private String status;

    private Date startTime;

    private Date lastTime;

    private Long timeout;
}
