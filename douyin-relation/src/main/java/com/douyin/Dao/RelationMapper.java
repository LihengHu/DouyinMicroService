package com.douyin.Dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RelationMapper {

    int insertRelation(int follower1,int follower2,int status);

    int selectStatus(int follower1,int follower2);

    int updateStatus(int follower1,int follower2 , int status);

    List<Integer> selectFollowList(int userId);

    List<Integer> selectFollowerList(int userId);
}
