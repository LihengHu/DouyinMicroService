package com.douyin.service;

import com.douyin.entity.Comment;

import java.util.List;

public interface CommentService {

    //添加一条评论
    Comment addComment(int uId,int vId, String content);

    //根据视频id查询出视频的所有评论

    /*
    * @param vId 视频id
    * */
    List<Comment> selectComments(int vid);

    //移除视频的一条评论
    /*
    * @parame id 评论id
    * */
    int removeComment(int id);

}
