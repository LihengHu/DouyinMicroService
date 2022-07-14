package com.douyin.entity;

import lombok.Data;

@Data
public class UserInfo {
    private int id;
    private String name;
    private int followCount;
    private int followerCount;
}
