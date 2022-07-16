package com.douyin.service;

import com.douyin.Dao.UserMapper;
import com.douyin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
     public User findUserByName(String username){
        return userMapper.selectByName(username);
    }
}
