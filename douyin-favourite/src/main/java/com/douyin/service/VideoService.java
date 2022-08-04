package com.douyin.service;

import com.douyin.entity.UserInfo;
import com.douyin.entity.VideoInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-video")
public interface VideoService {
    @GetMapping(value = "/userInfo",produces ="application/json;charset=utf-8")
    UserInfo finUserInfoById(@RequestParam("userId") int userId);

    @GetMapping(value = "/douyin/publish/get/video",produces ="application/json;charset=utf-8")
    VideoInfo getVideoById(@RequestParam("vid") int vId);
}
