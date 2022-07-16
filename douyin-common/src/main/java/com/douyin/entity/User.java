package com.douyin.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private int followCount;
    private int followerCount;
}
