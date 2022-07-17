package com.douyin.controller;

import com.douyin.entity.User;
import com.douyin.service.UserService;
import com.douyin.util.EncryptUtil;
import com.douyin.util.JsonUtil;
import com.douyin.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@RestController
public class LoginController {

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
        password = EncryptUtil.md5(password + user.getSalt());
        if (!password.equals(user.getPassword())){
            return JsonUtil.getJSONString(1,"密码错误",null);
        }
        String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), "user",null, user.getId());
        map.put("token",jwt);
        map.put("user_id",user.getId());
        return JsonUtil.getJSONString(0,"ok",map);
    }

    @PostMapping("/register")
    public String register(String username , String password){
        if(StringUtils.isBlank(username)){
            return JsonUtil.getJSONString(1,"用户名为空",null);
        }
        if(StringUtils.isBlank(password)){
            return JsonUtil.getJSONString(1,"密码为空",null);
        }
        HashMap<String, Object> msg = userService.register(username, password);
        if(msg.get("msg") != null){
            return JsonUtil.getJSONString(1, (String) msg.get("msg"),null);
        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("user_id",msg.get("userId"));
        String jwt = JwtUtil.createJWT(UUID.randomUUID().toString(), "user",null, msg.get("userId"));
        map.put("token",jwt);
        return JsonUtil.getJSONString(0,"注册成功",map);
    }
}
