package com.itheima.server.controller;

import com.itheima.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private UserService userService;
    /**
     * 获取登陆验证码
     * 请求参数：phone
     * 响应 void
     * ResponseEntity
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map map){
        String phone = (String) map.get("phone");
        userService.sendMsg(phone);
       return  ResponseEntity.status(200).body("success");
    }
    /**
     * 检验登陆
     * /user/loginVerification
     * phone   verificationConde
     */
    @PostMapping("/loginVerification")
    public ResponseEntity loginVerification(@RequestBody Map map){
        //1、调用map集合获取请求参数
        String phone = (String) map.get("phone");
        String code = (String) map.get("verificationCode");
        //2、调用userService完成用户登陆
      Map retMap =   userService.loginVerification(phone,code);
        //3、构造返回
        return ResponseEntity.ok(retMap);
    }
}
