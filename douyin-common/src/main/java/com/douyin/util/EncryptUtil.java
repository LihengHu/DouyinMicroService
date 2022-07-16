package com.douyin.util;


import org.springframework.util.DigestUtils;
import org.apache.commons.lang.StringUtils;
import java.util.UUID;

public class EncryptUtil {
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    //MD5加密
    public static String md5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
