package com.douyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class Comment {

    @JSONField(name = "id")
    private Integer id;

    private Integer uId;

    @JSONField(name = "user")
    private User user;

    private Integer vId;

    @JSONField(name = "content")
    private String content;

    private Date createTime;

    @JSONField(name = "create_date")
    private String createDay;

}
