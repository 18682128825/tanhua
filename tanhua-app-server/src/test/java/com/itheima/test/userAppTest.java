package com.itheima.test;

import com.itheima.server.AppServerApplication;
import com.tanhua.dubbo.api.UserApi;
import com.tanhua.model.domain.User;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = AppServerApplication.class)
@RunWith(SpringRunner.class)
public class userAppTest {
    @DubboReference
    private UserApi userApi;
    @Test
    public void getByMobileTest(){
        User user = userApi.findByMobile("13500000001");
        System.out.println(user);
    }
}
