package com.douyin.controller;

import com.douyin.Service.RelationService;
import com.douyin.util.JsonUtil;
import com.douyin.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelationController {

    @Autowired
    RelationService relationService;

    @PostMapping("/action")
    public String action(String token,int to_user_id,int action_type) throws Exception {
        Claims claims = JwtUtil.parseJWT(token);
        int userId = (int) claims.get("userId");
        if(action_type == 1){
            relationService.focus(userId,to_user_id,action_type);
        }else {
            relationService.unfocus(userId,to_user_id);
        }
        return JsonUtil.getJSONString(0,"操作成功");
    }

    @GetMapping("/follow/list")
    public String followList(String token) throws Exception {
        Claims claims = JwtUtil.parseJWT(token);
        int userId = (int) claims.get("userId");

        return "";
    }
}
