package com.douyin.controller;

import com.douyin.entity.User;
import com.douyin.entity.UserInfo;
import com.douyin.service.UserService;
import com.douyin.util.JsonUtil;
import com.douyin.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user")
    public String userInfo(String token) throws Exception {
        Claims claims = JwtUtil.parseJWT(token);
        int userId = (int) claims.get("userId");
        UserInfo user = userService.finUserInfoById(userId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("user",user);
        return JsonUtil.getJSONString(0,"获取用户信息成功",map);
    }

    @GetMapping("/userInfo")
    public UserInfo getUserInfo(int userId){
        return userService.finUserInfoById(userId);
    }

}
