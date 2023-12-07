package com.itheima.test;

import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testCreateToken(){
        //生成token
        Map map =  new HashMap<>();
        map.put("id",1);
        map.put("mobile","13800138000");
        //2、使用jwt的工具类生成token
        long now = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS512,"itcast")  //指定加密算法
                .setClaims(map)  //写入数据
                .setExpiration(new Date(now +30000))  //失效时间
                .compact();

        System.out.println(token);
    }

    @Test
    public void testParserToken(){
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJtb2JpbGUiOiIxMzgwMDEzODAwMCIsImlkIjoxLCJleHAiOjE3MDEwNzI1Mzh9.XwD0t9CS1WWhyJ_bZ2PC_2--_JKez2EeDmV0_7_vDP07fIrX-52FZxaWmDDuB1RIUg5RcSiTI6BOeobVXzkJWA1";
        try {
            Claims itcast = Jwts.parser().setSigningKey("itcast").parseClaimsJws(token).getBody();
            Object mobile = itcast.get("mobile");
            Object id = itcast.get("id");
            System.out.println(mobile+"-----"+id);
        } catch (ExpiredJwtException e) {
            System.out.println("token已过期");
        } catch (SignatureException e) { 
            System.out.println("token不合法");
        }
    }
}
