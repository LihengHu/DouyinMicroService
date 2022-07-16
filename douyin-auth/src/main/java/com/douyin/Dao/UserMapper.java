package com.douyin.Dao;

import com.douyin.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectByName(String username);

    User selectById(int id);

    int insertUser(User user);

}
