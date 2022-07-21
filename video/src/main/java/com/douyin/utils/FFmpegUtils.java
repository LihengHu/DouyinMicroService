package com.douyin.utils;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;



import java.awt.image.BufferedImage;

import java.io.InputStream;

import java.util.Random;


@Slf4j

public class FFmpegUtils {

    /*
       @param videFile  视频流
    * @return 生成的封面的 InputStream
    * */
    public static InputStream randomGrabberFFmpegImage(InputStream videFile) {

        FFmpegFrameGrabber grabber = null;

        try {

            grabber = new FFmpegFrameGrabber(videFile);
            if (grabber != null){
                grabber.start();
                // 获取视频时长， / 1000000 将单位转换为秒
                long delayedTime = grabber.getLengthInTime() / 1000000;

                Random random = new Random();

                // 跳转到响应时间 , 跳转到一个随机的中间帧
                grabber.setTimestamp((random.nextInt((int)delayedTime - 1) + 1) * 1000000L);
                Frame f = grabber.grabImage();
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage bi = converter.getBufferedImage(f);

                InputStream inputStreamPic = IOUtil.bufferedImageToInputStream(bi);

                return  inputStreamPic;

//
//                String   imageName = "ssdaaafsdf.jpg";
//                File out = Paths.get(path, imageName).toFile();
//                ImageIO.write(bi, "jpg", out);
            }


            return  null;
        } catch (Exception e) {
            log.info("视频封面生成出错！ {}",e.getMessage());
            return null;
        } finally {
            try {
                if (grabber != null) {
                    grabber.stop();
                    grabber.release();
                }
            } catch (FFmpegFrameGrabber.Exception e) {
                log.error("getVideoInfo grabber.release failed 获取文件信息失败：{}", e.getMessage());
            }
        }
    }


}

