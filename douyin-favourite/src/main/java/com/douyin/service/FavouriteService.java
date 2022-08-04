package com.douyin.service;

import com.douyin.Dao.FavouriteMapper;
import com.douyin.entity.Video;
import com.douyin.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class FavouriteService {

    @Autowired
    private FavouriteMapper favouriteMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    //点赞
    //查缓存是否存在
    public void like(int userId , int videoId ,int action_type){
        String videoLikeKey = RedisKeyUtil.getVideoLikeKey(videoId);
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        if(action_type == 1){
            favouriteMapper.insertVideoId(userId,videoId);//先更新数据库
        }else {
            favouriteMapper.updateStatus(userId,videoId,1);
        }
        //删缓存
        if(redisTemplate.hasKey(videoLikeKey)){
            redisTemplate.delete(videoLikeKey);
        }
        if(redisTemplate.hasKey(userLikeKey)){
            redisTemplate.delete(userLikeKey);
        }
    }

    public List<Integer> list(int userId){
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        if(redisTemplate.hasKey(userLikeKey)){
            List<Integer> members = (List)redisTemplate.opsForSet().members(userLikeKey);
            return members;
        }else{
            List<Integer> vids = favouriteMapper.selectVideoIdByUserId(userId);
            for (Integer vid:
                 vids) {
                redisTemplate.opsForSet().add(userLikeKey,vid);
            }
            return vids;
        }
    }
}
