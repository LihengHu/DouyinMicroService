package com.douyin.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Relation {
    private int id;
    private int follower1;
    private int follower2;
    private int status;
}
