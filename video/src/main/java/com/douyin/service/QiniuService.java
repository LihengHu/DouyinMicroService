package com.douyin.service;

import java.io.InputStream;

public interface QiniuService {
    String uploadImage2qiniu(InputStream in, String filepath);
}
