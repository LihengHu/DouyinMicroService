package com.douyin.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;

import java.util.Map;

public class JsonUtil {
    public static String getJSONString(int code, String msg , Map<String,Object> map){
        JSONObject json = new JSONObject();
        json.put("status_code",code);
        json.put("status_msg",msg);
        if(map != null){
            for (String key:
                    map.keySet()) {
                json.put(key,map.get(key));
            }
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg){
        return getJSONString(code,msg,null);
    }

    public static String getJSONString(int code){
        return getJSONString(code,null,null);
    }
}
