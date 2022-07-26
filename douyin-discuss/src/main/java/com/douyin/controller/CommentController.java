package com.douyin.controller;

import com.douyin.entity.Comment;
import com.douyin.entity.User;
import com.douyin.util.JsonUtil;
import com.douyin.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/douyin/comment/action/")
    public String doComment(@RequestParam("video_id") int vId, @RequestParam("action_type") int actionType ,@RequestParam(name = "comment_text" ,required = false) String content
    ,@RequestParam(name = "comment_id",required = false) int commentId
    ){

        //判断视频是否存在

        if(actionType == 1) { //发布评论
           Comment comment =  commentService.addComment(1,vId,content);

           if (comment == null)
               return JsonUtil.getJSONString(400,"添加失败") ;

            // 获取用户信息   **************
            User user = new User();
            user.setFollowCount(11);
            user.setFollowerCount(55);
            user.setName("王五");
            user.setUsername("aaa");

            comment.setUser(user);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" MM-dd");

            String now = simpleDateFormat.format(comment.getCreateTime());

            comment.setCreateDay(now);

            HashMap<String ,Object> map = new HashMap<>();

            map.put("comment",comment);

            return JsonUtil.getJSONString(200,"添加成功",map) ;
        }
        if (actionType == 2){  //删除

            int re = commentService.removeComment(commentId);

            if (re>0){
                log.info(" 删除 {}",re);

                return JsonUtil.getJSONString(200,"删除成功");
            }else {
                return JsonUtil.getJSONString(200,"删除失败");
            }


        }


        return JsonUtil.getJSONString(400,"操作出错");


    }


    @GetMapping("/douyin/comment/list/")

    public String getComments(@RequestParam("video_id") int vId){

        List<Comment> comments = commentService.selectComments(vId);
        if (comments == null || comments.size() <= 0)
            return JsonUtil.getJSONString(200,"无数据");

        HashMap<String ,Object> map = new HashMap<>();
        List<Comment>  reComments = new ArrayList<>();

        for (Comment comment:comments){

            // 获取用户信息   **************
            User user = new User();
            user.setFollowCount(11);
            user.setFollowerCount(55);
            user.setName("王五");
            user.setUsername("aaa");

            comment.setUser(user);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" MM-dd");

            String now = simpleDateFormat.format(comment.getCreateTime());

            comment.setCreateDay(now);

            reComments.add(comment);

        }

        map.put("comment_list",reComments);

        return JsonUtil.getJSONString(200, "查询成功", map);

    }

}
