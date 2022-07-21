package com.douyin.service;

import com.douyin.entity.Video;

import java.util.List;

public interface VideoService {

    Integer addVideo(Video video);
    Video selectVideoById(Integer id);
    Integer publisVideo(String videoUrl, String picUrl, Integer uId , String title);

    //获取小于时间戳lastTime 的最新 30 条视频   对应的id列表
    List<Integer>  getFeedsId(long lastTime);

    //根据视频id列表，获取 视频详情列表
    List<Video> getVideoList(List<Integer> vIds);

    //获取视频 feed流
    List<Video> getVideoFeed(long lastTime);

    List<Video> getPublishList(Integer uId);


}
