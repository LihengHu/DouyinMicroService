package com.douyin.Dao;

import com.douyin.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Mapper
public interface VideoMapper {
    Integer addVideo(Video video);
    Video selectById(Integer id);
    List<Integer> getFeeds(Date lastTime);
    List<Integer> getPublishList(Integer uId);
}
