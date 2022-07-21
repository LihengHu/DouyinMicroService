package com.douyin.utils;

import org.bytedeco.opencv.presets.opencv_core;

public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String VIDEO_DETAIL = "detail:video";
    private static final String PUBLISH_LIST = "publish:video";

    public static String getVideoDetailKey(int vId){
        return VIDEO_DETAIL+SPLIT+vId;
    }

    public static String getPublishKey(int uId){
        return PUBLISH_LIST+SPLIT+uId;
    }



}
