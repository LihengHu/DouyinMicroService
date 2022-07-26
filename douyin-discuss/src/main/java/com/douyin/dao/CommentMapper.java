package com.douyin.dao;


import com.douyin.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    int addComment(Comment comment);

    List<Comment> selectAllByvId(int vId);

    Integer removeById(int id);
}
