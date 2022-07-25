package com.douyin.util;

public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String PREFIX_USER_LIKE  = "like:user";
    private static final String PREFIX_VIDEO_LIKE = "like:video";

    public static String getUserLikeKey(int userId){
        return PREFIX_USER_LIKE + SPLIT + userId;
    }
    public static String getVideoLikeKey(int videoId){
        return PREFIX_VIDEO_LIKE + SPLIT + videoId;
    }
}
