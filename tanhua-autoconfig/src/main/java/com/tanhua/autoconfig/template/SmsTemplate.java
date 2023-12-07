package com.tanhua.autoconfig.template;

import com.tanhua.autoconfig.properties.SmsProperties;

public class SmsTemplate {
    private SmsProperties properties;
    public SmsTemplate(SmsProperties smsProperties){
        this.properties = smsProperties;
    }
    public void sendSms(String mobile,String code){
        System.out.println(mobile);
        System.out.println("+++++++++++"+properties.toString());
        System.out.println(code);
    }
}
