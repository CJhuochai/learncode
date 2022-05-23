package com.example.demo.controller;

import com.example.demo.annotation.Limit;
import com.example.demo.annotation.Lock;
import com.example.demo.annotation.RequestLock;
import com.example.demo.dao.UserMapper;
import com.example.demo.entity.DbUser;
import com.example.demo.entity.User;
import com.example.demo.service.ServiceDemo;
import com.example.demo.util.RedisUtils;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: limiting-demo
 * @description: 测试
 * @author: Jian Chen
 * @create: 2021-10
 **/
@Slf4j
@RestController
@RequestMapping("/test1")
public class TestController {
    @Autowired
    private ServiceDemo serviceDemo;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserMapper userMapper;

    private int a = 100;

    /**
     * 限流策略：1秒钟2个请求
     */
    private final RateLimiter limiter = RateLimiter.create(2.0);

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/nihao")
    public String test1(){
        //500毫秒内，没拿到令牌，就直接进入服务降级
        boolean b = limiter.tryAcquire(500, TimeUnit.MILLISECONDS);
        if (!b){
            log.warn("进入服务降级，时间{}", LocalDateTime.now().format(dtf));
            return "当前排队人数较多，请稍后再试！";
        }
        log.info("获取令牌成功，时间{}", LocalDateTime.now().format(dtf));
        return "请求成功";
    }

    @GetMapping("/text")
    @RequestLock(num = 1)
    public String text(){
        int a = 1 / 0;
        return "请求成功";
    }

    @GetMapping("/async-test")
    public String threadTest1(){
        serviceDemo.asyncTest1();
        return "主线程请求完毕";
    }

    @GetMapping("/log")
    public String threadTest1(String id,String name,String host){
        return "主线程请求完毕";
    }

    @GetMapping("/redis01")
    public Object redis01(String name,Integer age) {
        User user = new User(name,age);
        redisUtils.set(name, user);
        return redisUtils.get(name);
    }
    @GetMapping("/redis02")
    @Cacheable( cacheNames = {"redis02"},key = "#name")
    public Object redis02(String name,Integer age) {
        User user = new User(name,age);
        return user;
    }

    @GetMapping("/users")
    @Cacheable( cacheNames = {"users"},key = "#root.methodName")
    public Object users(){
        final List<DbUser> dbUsers = this.userMapper.selectByMap(null);
        return dbUsers;
    }

    @Limit(key = "#id",permitsPerSecond = 3,timeout = 10L,timeunit = TimeUnit.SECONDS)
    @Cacheable( cacheNames = {"usersCount"},key = "#root.methodName")
    @GetMapping("/usersCount")
    public Object usersCount(String id){
        return this.userMapper.count();
    }

    /**
     * 秒杀
     * @return
     */
    @Lock(key = "CJ",waitTime = 500L,timeOut = 15000L,timeUnit = TimeUnit.MILLISECONDS)
    @GetMapping("/spike")
    public Object spike(){
        --a;
        if (a < 1){
            return "秒杀完毕";
        }
        log.info("-----------------------------当前剩余a:{}",a);
        return a;
    }

    @GetMapping("/a")
    public Object a(){
        this.serviceDemo.a();
        return null;
    }
}
