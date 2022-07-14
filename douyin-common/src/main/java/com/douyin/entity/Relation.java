package com.douyin.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Relation {
    private int id;
    //被关注者
    private int followId;
    //关注者
    private int followerId;
    private int status;
    private Date createTime;
    private Date updateTime;
}
