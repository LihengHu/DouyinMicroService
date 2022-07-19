package com.douyin.controller;

import com.douyin.util.JsonUtil;
import com.douyin.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelationController {

    @GetMapping("/action")
    public String action(String token,int to_user_id,int action_type) throws Exception {
        Claims claims = JwtUtil.parseJWT(token);
        int userId = (int) claims.get("userId");
        return "";
    }
}
