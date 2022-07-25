package com.douyin.service;

import com.douyin.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

public class FavouriteService {

    @Autowired
    private RedisTemplate redisTemplate;

    //点赞
    public void like(int userId , int videoId ,int action_type){
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
                String videoLikeKey= RedisKeyUtil.getVideoLikeKey(videoId);
                operations.multi();
                if(action_type == 2){
                    redisTemplate.opsForSet().remove(userLikeKey,videoId);
                    redisTemplate.opsForValue().decrement(videoLikeKey);
                }else if(action_type == 1){
                    redisTemplate.opsForSet().add(userLikeKey,videoId);
                    redisTemplate.opsForValue().increment(videoLikeKey);
                }
                return operations.exec();
            }
        });
    }
}
