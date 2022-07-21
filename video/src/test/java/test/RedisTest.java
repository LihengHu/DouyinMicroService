package test;

import com.douyin.VideoApplication;
import com.douyin.entity.Video;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest(classes = VideoApplication.class)
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        Video video = new Video();
        video.setCreateTime(new Date());
        video.setTitle("asds");
        redisTemplate.opsForValue().set("aa",video);

        Video vv = (Video) redisTemplate.opsForValue().get("bb");
        System.out.println(vv);


    }


}
