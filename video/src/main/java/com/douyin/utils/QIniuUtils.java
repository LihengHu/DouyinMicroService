package com.douyin.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Objects;
import java.util.UUID;

public class QIniuUtils
{   public final static String JPG_SUFFIX = ".jpg";
    public final static String PNG_SUFFIX = ".png";
    // 根据文件名，生成UUID作为新的文件名
    public static String generateFileName(String filename)
    {
        Objects.requireNonNull(filename,"文件名为null");
        int index=filename.lastIndexOf('.');
        if(StringUtils.isBlank(filename) || index==-1)
        {
            throw new IllegalArgumentException("文件名不合法");
        }
        String suffix=filename.substring(index);
        String uuid= UUID.randomUUID().toString().replaceAll("-","");
        return uuid+suffix;
    }

    //生成UUID作为新的图片名
    /*
    *  suffix 图片文件类型 ，JPG或者PNG
    * */
    public static String getPictureName(String suffix){
        String uuid= UUID.randomUUID().toString().replaceAll("-","");
        return uuid+suffix;
    }

    // 从图片URL中提取Key
    // http://quwf2a7om.hn-bkt.clouddn.com/xxx/11c85acc16f24361ba97999185fb0469.jpeg
    public static String getKey(String imageUrl)
    {
        if(imageUrl==null || imageUrl.trim().isEmpty())
        {
            return null;
        }
        return imageUrl.substring(imageUrl.indexOf(".com")+5);
    }
}
