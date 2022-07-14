package com.douyin.entity;

import lombok.Data;

import java.util.Date;

@Data
public class VideoLike {
    private int id;
    private int userId;
    private int videoId;
    private Date createTime;
}
