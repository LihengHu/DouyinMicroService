package com.douyin.service.impl;

import com.douyin.config.QiniuProperties;
import com.douyin.service.QiniuService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.storage.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class QiniuServiceImpl implements QiniuService
{
    private final String domain;
    private final String bucketName;

    // 七牛文件上传管理器
    private final Configuration cfg;
    private final Auth auth;

    @Autowired
    public QiniuServiceImpl(QiniuProperties oss)
    {
        String ak = oss.getAccessKey();
        String sk = oss.getSecretKey();
        this.domain = oss.getDomain(); // CDN域名
        this.bucketName = oss.getBucketName();


        // //构造一个带指定 Region 对象的配置类
        cfg = new Configuration(Region.huanan());
        System.out.println("peizhi " + ak+"  "+sk);
        auth = Auth.create(ak,sk);


    }

    /**
     * 上传图片或者视频到七牛云
     * @return 图片或者视频url
     * */
    @Override
    public String uploadImage2qiniu(InputStream in, String key)
    {
        try {
            UploadManager uploadManager = new UploadManager(cfg);
            // 根据命名空间生成的上传token
            System.out.println(bucketName+"  ********");
            String upToken = auth.uploadToken(bucketName);
            Response response = uploadManager.put(in,key,upToken,null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //System.out.println(putRet.key);
            //System.out.println(putRet.hash);
            return String.format("http://%s/%s",this.domain,putRet.key);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
