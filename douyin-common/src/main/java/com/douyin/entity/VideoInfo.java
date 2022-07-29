package com.douyin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class VideoInfo {

    @JSONField(name = "id")
    private int id;

    @JSONField(name = "author")
    private UserInfo author;

    @JSONField(name = "play_url")
    private String playUrl;

    @JSONField(name = "cover_url")
    private String coverUrl;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "favorite_count")
    private int favoriteCount;

    @JSONField(name = "comment_count")
    private int commentCount;

    @JSONField(name = "is_favorite")
    private Boolean favorite = false;
}
