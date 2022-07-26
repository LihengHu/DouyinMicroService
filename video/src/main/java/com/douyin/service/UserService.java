package com.douyin.service;


import com.douyin.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-auth")
public interface UserService {

    @GetMapping(value = "/userInfo")
     UserInfo getUserInfo(@RequestParam("userId") Integer userId);
}
