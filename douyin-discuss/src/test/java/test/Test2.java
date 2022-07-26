package test;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test2 {

    @Test
    public void test1(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" MM-dd");

        String now = simpleDateFormat.format(new Date());

        System.out.println(now);

    }
}
