package com.itheima.server.service;

import ch.qos.logback.classic.Logger;
import com.tanhua.autoconfig.template.SmsTemplate;
import com.tanhua.commons.utils.JwtUtils;
import com.tanhua.dubbo.api.UserApi;
import com.tanhua.model.domain.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private SmsTemplate smsTemplate;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @DubboReference
    private UserApi userApi;
    /**
     * 发送短信验证码
     * @param phone
     */
    public void sendMsg(String phone) {
        //1、随机生成6位数字
//        String code = RandomStringUtils.randomNumeric(6);
        String code = "123456";
        //2、调用template对象，发送手机短信
//        smsTemplate.sendSms(phone,code);
        //3、将验证码存入redis
        redisTemplate.opsForValue().set("CHECK_CODE"+phone,code, Duration.ofMinutes(5));
    }

    /**
     * 验证登陆
     * @param phone
     * @param code
     * @return
     */
    public Map loginVerification(String phone, String code) {
       //1、从redis中获取下发的验证码
        String redisCode = redisTemplate.opsForValue().get("CHECK_CODE"+phone);
        System.out.println("====="+redisCode);
        //2、对验证码进行校验
        if(Strings.isEmpty(redisCode) || !redisCode.equals(code)){
            //验证码无效
            throw new RuntimeException("验证码错误");
        }
        //3、删除redis中的验证码
        redisTemplate.delete("CHECK_CODE" + phone);
        //4、通过手机号查询用户
        User user = userApi.findByMobile(phone);
        Boolean isNew = false;
        //5、如果用户不存在、创建新用户保存到数据库中
        if(user == null){
            user = new User();
            user.setCreated(new Date());
            user.setUpdated(new Date());
            user.setPassword(DigestUtils.md5Hex("123456"));
            Long userId =  userApi.save(user);
            user.setId(userId);
            isNew = true;
        }
        //6、通过JWT生成token 存入id和手机号码
        Map tokenMap = new HashMap();
        tokenMap.put("id",user.getId());
        tokenMap.put("mobile",phone);
        String token = JwtUtils.getToken(tokenMap);
        //7、构造返回2值
         Map restMap = new HashMap();
         restMap.put("token",token);
         restMap.put("isNew",isNew);

        return restMap;
    }
}
