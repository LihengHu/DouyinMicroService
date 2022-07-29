package com.douyin.controller;


import com.douyin.entity.User;
import com.douyin.entity.UserInfo;
import com.douyin.entity.Video;
import com.douyin.entity.VideoInfo;
import com.douyin.service.QiniuService;
import com.douyin.service.UserService;
import com.douyin.service.VideoService;
import com.douyin.util.JsonUtil;
import com.douyin.util.JwtUtil;
import com.douyin.utils.FFmpegUtils;
import com.douyin.utils.IOUtil;
import com.douyin.utils.QIniuUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@Slf4j
@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private  QiniuService qiniuService;

    @Autowired
    private UserService userService;

    @PostMapping("/douyin/publish/action")
    public String publish(HttpServletRequest request,String token) throws Exception {

        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);

        Claims claims = JwtUtil.parseJWT(token);
        int userId = (int) claims.get("userId");



        MultipartFile file = params.getFile("data");  //视频文件
        String title=params.getParameter("title");  //视频标题
        System.out.println("title:"+title);

        //视频地址
        String videoUrl ;
        //图片地址
        String picUrl;

        //复制InputStream 的容器
        ByteArrayOutputStream baos = null;
        if (file.isEmpty()){
            return JsonUtil.getJSONString(500,"视频上传出错,视频为空");
        }

        try  (InputStream inputStream=file.getInputStream()) {

             baos = IOUtil.copyStream(inputStream);
            //构造同样数据的不同流对象
            //用于生成封面
            InputStream inputStreamCopyPic = new ByteArrayInputStream(baos.toByteArray());
            //用于视频上传
            InputStream inputStreamCopyVideo = new ByteArrayInputStream(baos.toByteArray());
            
            //根据视频流生成封面
            InputStream picFrame =  FFmpegUtils.randomGrabberFFmpegImage(inputStreamCopyPic);
            //图片名称
            String Videoname = file.getOriginalFilename();

            String videokey = "video/"+ QIniuUtils.generateFileName(Videoname);
            String picKey = "pic/"+QIniuUtils.getPictureName(QIniuUtils.PNG_SUFFIX);

            videoUrl = qiniuService.uploadImage2qiniu(inputStreamCopyVideo,videokey);
            picUrl = qiniuService.uploadImage2qiniu(picFrame,picKey);
            if(videoUrl == null ||  picUrl == null)
            {
                log.info("文件上传失败 ！");
                return JsonUtil.getJSONString(500,"视频上传出错！网络错误！");
            }
        } catch (Exception e) {

           log.info("视频上传出错！{}",e.getMessage());
            return JsonUtil.getJSONString(500,"视频上传出错！网络错误！");
        }finally {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        int uId = 1 ; //获取用户id

        int re = videoService.publisVideo( videoUrl,  picUrl,  uId , title);

        if (re <= 0)
            return JsonUtil.getJSONString(500,"视频上传出错！网络错误！");


        return JsonUtil.getJSONString(200,"视频上传成功！" + videoUrl + "   pic: "+ picUrl );
    }

    @GetMapping("/douyin/feed")
    public String getFeed(@RequestParam(defaultValue = "0")  long latest_time , String token){

        if (latest_time == 0l){  //如果前端没有传入时间戳，则使用当前时间
            latest_time = new Date().getTime();
        }

        List<Video> videoList = videoService.getVideoFeed(latest_time);
        if (videoList == null)
            return JsonUtil.getJSONString(200,"暂时没有数据");

        List<VideoInfo> reVideoList = new ArrayList<>();
        for (Video video : videoList){
            UserInfo userInfo = userService.getUserInfo(video.getUId());
            if (userInfo == null)
                continue;
            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setId(video.getId());
            videoInfo.setAuthor(userInfo);
            videoInfo.setCoverUrl(video.getCoverUrl());
            videoInfo.setPlayUrl(video.getPlayUrl());
            videoInfo.setCommentCount(video.getCommentCount());
            videoInfo.setFavoriteCount(video.getFavoriteCount());

//            boolean isLike = likeService.isLikeVideo(vid,uid);

            videoInfo.setFavorite(video.getFavorite());
            videoInfo.setTitle(video.getTitle());
            reVideoList.add(videoInfo);
        }
        long lastTime =  videoList.get(0).getCreateTime().getTime();
        HashMap<String,Object> map = new HashMap<>();
        map.put("next_time",lastTime);
        map.put("video_list",reVideoList);


        return JsonUtil.getJSONString(200,"查询成功",map);

    }

    @GetMapping("/douyin/publish/list/")
    public String getPublishList(@RequestParam(name = "user_id") Integer uid ,String token){
        if (uid == null || uid == 0)
            return JsonUtil.getJSONString(404,"没有用户id");

        //判断用户是否存在 ******
        List<Video> videoList = videoService.getPublishList(uid);
        System.out.println(videoList.size()+"*********");
        if (videoList == null ||videoList.size()<=0){
            return JsonUtil.getJSONString(200,"没有发布视频");
        }

        List<Video> reVideoList = new ArrayList<>();
        UserInfo userInfo = userService.getUserInfo(uid);

        for (Video video : videoList){
            // 获取用户信息   **************

            video.setAuthor(userInfo);
            reVideoList.add(video);

        }
        long lastTime =  reVideoList.get(0).getCreateTime().getTime();
        HashMap<String,Object> map = new HashMap<>();
        map.put("video_list",reVideoList);

        return JsonUtil.getJSONString(200,"查询成功",map);
    }

    @GetMapping("/douyin/publish/get/video")
    public VideoInfo getVideoById(int vId){

        Video video =  videoService.selectVideoById(vId);
        VideoInfo videoInfo = new VideoInfo();

        if (video != null){
            UserInfo userInfo = userService.getUserInfo(video.getUId());
            if (userInfo == null){
                videoInfo.setId(-1);  //videoInfo id 为 -1 则视频信息获取失败
                return videoInfo;
            }

            videoInfo.setId(video.getId());
            videoInfo.setAuthor(userInfo);
            videoInfo.setCoverUrl(video.getCoverUrl());
            videoInfo.setPlayUrl(video.getPlayUrl());
            videoInfo.setCommentCount(video.getCommentCount());
            videoInfo.setFavoriteCount(video.getFavoriteCount());
            videoInfo.setFavorite(video.getFavorite());
            videoInfo.setTitle(video.getTitle());

            return videoInfo;
        }

        videoInfo.setId(-1);  //videoInfo id 为 -1 则视频信息获取失败
        return videoInfo;
    }

    @GetMapping("/douyin/hasVIdeo")
    public Boolean hasVideo(int vId){
        Video video =  videoService.selectVideoById(vId);
        if (video == null)
            return false;
        return true;
    }
}

