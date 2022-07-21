package com.douyin.controller;

import com.douyin.service.QiniuService;
import com.douyin.utils.QIniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class QiniuController
{
    private final QiniuService qiniuService;

    @Autowired
    public QiniuController(QiniuService qiniuService)
    {
        this.qiniuService = qiniuService;
    }

    @PostMapping("/image/upload")
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException
    {
        if(!file.isEmpty())
        {
            InputStream in = file.getInputStream();
            String filename = file.getOriginalFilename();
            try
            {
                String key = "xxx/"+ QIniuUtils.generateFileName(filename);
                String fileUrl = qiniuService.uploadImage2qiniu(in,key);
                if(fileUrl!=null)
                {
                    return "上传成功: "+fileUrl;
                }
            }
            catch(IllegalArgumentException e)
            {
                e.printStackTrace();
            }
        }
        return "上传失败";
    }
}
