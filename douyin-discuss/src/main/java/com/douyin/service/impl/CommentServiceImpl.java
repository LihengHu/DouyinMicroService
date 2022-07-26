package com.douyin.service.impl;

import com.douyin.entity.Comment;
import com.douyin.dao.CommentMapper;
import com.douyin.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public Comment addComment(int uId, int vId, String content) {

        Comment comment = new Comment();

        comment.setUId(uId);
        comment.setVId(vId);
        comment.setContent(content);
        comment.setCreateTime(new Date());

        int re = commentMapper.addComment(comment);
        if (re>0)
            return comment;

        return null;


    }

    @Override
    public List<Comment> selectComments(int vid) {



        return commentMapper.selectAllByvId(vid);
    }

    @Override
    public int removeComment(int id) {

        return commentMapper.removeById(id) ;
    }
}
