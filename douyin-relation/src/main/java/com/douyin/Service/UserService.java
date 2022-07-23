package com.douyin.Service;

import com.douyin.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-auth")
public interface UserService {
    @GetMapping(value = "/userInfo",produces ="application/json;charset=utf-8")
    UserInfo finUserInfoById(@RequestParam("userId") int userId);
}
