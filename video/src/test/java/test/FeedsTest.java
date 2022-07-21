package test;

import com.douyin.VideoApplication;
import com.douyin.service.VideoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = VideoApplication.class)
@RunWith(SpringRunner.class)
public class FeedsTest {

    @Autowired
    private VideoService videoService;


    @Test
    public void  testFeed(){
        List<Integer> re = videoService.getFeedsId(new Date().getTime());
        System.out.println(re.size());
    }
}
