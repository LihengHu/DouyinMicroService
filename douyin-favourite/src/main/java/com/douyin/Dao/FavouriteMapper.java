package com.douyin.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavouriteMapper {

    List<Integer> selectUserIdByVideoId(int videoId);
    List<Integer> selectVideoIdByUserId(int userId);
    int insertVideoId(int userId ,int videoId);
    int updateStatus(int userId ,int videoId,int cancel);
}
