package com.itheima.test;

import com.itheima.server.AppServerApplication;
import com.tanhua.autoconfig.template.SmsTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppServerApplication.class)
public class SmsTemplateTest {
    //注入
    @Autowired
   private SmsTemplate smsTemplate;

    @Test
    public void testSenSms(){
        smsTemplate.sendSms("1355541","7788");
    }
}
