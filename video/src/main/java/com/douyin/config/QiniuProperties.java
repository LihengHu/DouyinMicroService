package com.douyin.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix = "oss.qiniu")
@Data
public class QiniuProperties
{   @Value("${oos.qiniu.domain}")
    private String domain; // CDN域名
    @Value("${oos.qiniu.accessKey}")
    private String accessKey; // ACCESS_KEY
    @Value("${oos.qiniu.secretKey}")
    private String secretKey; // SECRET_KEY
    @Value("${oos.qiniu.bucketName}")
    private String bucketName; // 空间名称
}
