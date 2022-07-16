package com.douyin.controller;

import com.alibaba.fastjson.JSON;
import com.douyin.entity.User;
import com.douyin.service.UserService;
import com.douyin.util.JsonUtil;
import com.douyin.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

import static java.awt.SystemColor.info;

@RestController
public class loginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(String username ,String password){
        User user = userService.findUserByName(username);
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Integer> userMap = new HashMap<>();
        userMap.put("userId",user.getId());
        String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(userMap),null);
        map.put("token",jwt);
        map.put("user_id",user.getId());
        return JsonUtil.getJSONString(200,"ok",map);
    }
}
