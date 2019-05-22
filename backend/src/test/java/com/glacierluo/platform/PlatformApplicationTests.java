package com.glacierluo.platform;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlatformApplicationTests {



    @Test
    public void contextLoads() {
        String a="123456";
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

        System.out.println(bCryptPasswordEncoder.encode(a));
    }

}
