package test;

import org.bytedeco.opencv.presets.opencv_core;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {

    @Test
    public void test1() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = "2018-09-22 16:39:00";
        Date date = format.parse(time);

        //日期转时间戳
        long t = date.getTime();
        System.out.println("tt: "+t);

        Date date1 = new Date(t);

        String d = format.format(date1);
        System.out.println(d);
        long s = 20180922163900L;

        System.out.println(new Date().getTime());

    }

}
