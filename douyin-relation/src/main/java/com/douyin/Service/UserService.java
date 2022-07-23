package com.douyin.Service;

import com.douyin.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "service-auth",url = "localhost/")
public interface UserService {
    @GetMapping("/userInfo")
    UserInfo finUserInfoById(int userId);
}
