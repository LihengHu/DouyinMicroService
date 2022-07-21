package com.douyin.Dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelationMapper {

    int insertRelation(int follower1,int follower2,int status);

    int selectStatus(int follower1,int follower2);

    int updateStatus(int follower1,int follower2 , int status);
}
