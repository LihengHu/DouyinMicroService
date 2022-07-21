package com.douyin.entity.extend;

import com.douyin.entity.User;
import com.douyin.entity.Video;
import lombok.Data;

@Data
public class VideoExtend extends Video {
   private User author;

}
