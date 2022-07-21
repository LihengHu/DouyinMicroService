package com.douyin.service;

import com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import com.douyin.Dao.UserMapper;
import com.douyin.entity.User;
import com.douyin.entity.UserInfo;
import com.douyin.util.EncryptUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    public User findUserByName(String username){
        return userMapper.selectByName(username);
    }

    public HashMap<String, Object> register(String username, String password){
        HashMap<String,Object> map = new HashMap<>();
        User user = userMapper.selectByName(username);
        if(user != null){
            map.put("msg","该帐号已存在");
            return map;
        }
        user = new User();
        user.setName("name");
        user.setUsername(username);
        user.setSalt(EncryptUtil.generateUUID().substring(0, 5));//生成盐
        user.setPassword(EncryptUtil.md5(password + user.getSalt()));
        user.setFollowCount(0);
        user.setFollowerCount(0);
        userMapper.insertUser(user);
        map.put("userId",user.getId());
        return map;
    }

    public UserInfo finUserInfoById(int userId){
        User user = userMapper.selectById(userId);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setFollow_count(user.getFollowCount());
        userInfo.setFollower_count(user.getFollowerCount());
        userInfo.setName("name");
        userInfo.setIs_follow(true);
        return userInfo;
    }

}
