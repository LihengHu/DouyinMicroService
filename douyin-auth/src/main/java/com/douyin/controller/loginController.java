package com.douyin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import com.douyin.entity.User;
import com.douyin.service.UserService;
import com.douyin.util.JsonUtil;
import com.douyin.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
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
    public String login(String username ,String password) throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        if(StringUtils.isBlank(username)){
            return JsonUtil.getJSONString(1,"用户名为空",null);
        }
        if(StringUtils.isBlank(password)){
            return JsonUtil.getJSONString(1,"密码为空",null);
        }
        User user = userService.findUserByName(username);
        if(user == null){
            return JsonUtil.getJSONString(1,"用户不存在",null);
        }
        HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("userId",user.getId());
        String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), "user",null, userMap);
        Claims claims = JwtUtil.parseJWT(jwt);
        map.put("token",jwt);
        map.put("user_id",user.getId());
        return JsonUtil.getJSONString(0,"ok",map);
    }
}
