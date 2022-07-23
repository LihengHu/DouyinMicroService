package com.douyin.Service;

import com.douyin.Dao.RelationMapper;
import com.douyin.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationService {

    @Autowired
    RelationMapper relationMapper;

    public void focus(int follower1 ,int follower2 , int status){
        if(follower1 <follower2){
            relationMapper.insertRelation(follower1,follower2,1);
        }else {
            relationMapper.insertRelation(follower2,follower1,2);
        }
    }


    public void unfocus(int follower1 , int follower2){
        if(follower1 < follower2){
            int status = relationMapper.selectStatus(follower1,follower2);
            if(status == 1){
                relationMapper.updateStatus(follower1,follower2,0);
            }
            else if(status == 3){
                relationMapper.updateStatus(follower1,follower2,2);
            }
        }
        else {
            int status = relationMapper.selectStatus(follower2,follower1);
            if(status == 2){
                relationMapper.updateStatus(follower2,follower1,0);
            }
            else if(status == 3){
                relationMapper.updateStatus(follower2,follower1,1);
            }
        }
    }

    public List<Integer> findFollowList(int userId){
        List<Integer> follows = relationMapper.selectFollowList(userId);
        return follows;
    }

    public List<Integer> findFollowerList(int userId){
        return relationMapper.selectFollowerList(userId);
    }

}
