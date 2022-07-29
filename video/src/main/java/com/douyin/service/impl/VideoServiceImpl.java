package com.douyin.service.impl;

import com.douyin.Dao.VideoMapper;
import com.douyin.entity.Video;
import com.douyin.service.VideoService;
import com.douyin.utils.RedisKeyUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Integer addVideo(Video video) {
        int re = videoMapper.addVideo(video);

        if (re > 0) {
            int vId = video.getId();
            String videoKey = RedisKeyUtil.getVideoDetailKey(vId);
            redisTemplate.opsForValue().set(videoKey, video);
        }

        return re;
    }


    @Override
    public Video selectVideoById(Integer id) {

        //先查询 redis
        Video video;

        String videoKey = RedisKeyUtil.getVideoDetailKey(id);
        log.info("先查Redis****");
        video = (Video) redisTemplate.opsForValue().get(videoKey);

        if (video == null) {
            //未命中 则查询 MySQL
            video = videoMapper.selectById(id);

            log.info("查数据库*****");
            //video写入redis
            if (video != null) {
                redisTemplate.opsForValue().set(videoKey, video);
            }

        }

        return video;
    }

    @Override
    public Integer publisVideo(String videoUrl, String picUrl, Integer uId, String title) {

        Video video = new Video();

        video.setUId(uId);
        video.setPlayUrl(videoUrl);
        video.setCoverUrl(picUrl);
        video.setTitle(title);
        video.setFavoriteCount(0);
        video.setCommentCount(0);
        video.setCreateTime(new Date());

        int re = videoMapper.addVideo(video);
        if (re > 0) {   //视频写入数据库

            int vId = video.getId();
            String videoKey = RedisKeyUtil.getVideoDetailKey(vId);

            //视频id加到用户发布列表
            redisTemplate.opsForValue().set(videoKey, video);

            String publishKey = RedisKeyUtil.getPublishKey(uId);

            redisTemplate.opsForList().leftPush(publishKey, vId);
        }

        return re;

    }

    @Override
    public List<Integer> getFeedsId(long lastTime) {
        return videoMapper.getFeeds(new Date(lastTime));
    }

    @Override
    public List<Video> getVideoList(List<Integer> vIds) {
        List<Video> videoList = new ArrayList<>();

        if (vIds.size() <= 0)
            return null;


        for (Integer vId : vIds) {
            Video video = selectVideoById(vId);
            videoList.add(video);
        }

        return videoList;
    }

    @Override
    public List<Video> getVideoFeed(long lastTime) {
        List<Integer> ids = getFeedsId(lastTime);

        return getVideoList(ids);

    }

    @Override
    public List<Video> getPublishList(Integer uId) {

        if (uId == null || uId == 0)
            return null;

        String publishKey = RedisKeyUtil.getPublishKey(uId);

        List<Integer> vIds = redisTemplate.opsForList().range(publishKey, 0, -1);
        if (vIds == null || vIds.size() == 0) {
            vIds = videoMapper.getPublishList(uId);

            //把发布列表放到redis
            for (Integer id : vIds) {
                redisTemplate.opsForList().leftPush(publishKey, id);
            }

        }

        if (vIds == null || vIds.size() == 0)
            return null;
        List<Video> videoList = getVideoList(vIds);

        if (videoList != null && videoList.size() > 0)
            return videoList;
        return null;
    }
}
