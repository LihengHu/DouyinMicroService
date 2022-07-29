package com.douyin.controller;

import com.douyin.service.FavouriteService;
import com.douyin.util.JsonUtil;
import com.douyin.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

public class  FavouriteController {

    @Autowired
    FavouriteService favouriteService;

    @PostMapping
    public String action(String token,int video_id,int action_type) throws Exception {
        Claims claims = JwtUtil.parseJWT(token);
        int userId = (int) claims.get("userId");
        favouriteService.like(userId,video_id,action_type);
        return JsonUtil.getJSONString(200,"操作成功");
    }
}
