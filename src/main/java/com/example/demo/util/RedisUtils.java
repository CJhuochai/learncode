package com.example.demo.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-12
 **/
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String key, Object valuet) {
        this.set(key, valuet, null,null);
    }

    public void set(String key, Object value, Long timeOut,TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(key, value);
        if (null != timeOut)
            this.redisTemplate.expire(key, timeOut, timeUnit);
    }

    public Object get(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }


    public void setList(String key, List<Object> values, Long timeOut) {
        values.forEach(value -> this.redisTemplate.opsForList().leftPush(key, value));
        this.redisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
    }

    public boolean hasKey(String key){
        return this.redisTemplate.hasKey(key);
    }

    public void lpush(String key,Object value){
        this.redisTemplate.opsForList().leftPush(key,value);
    }

    public Object rPop(String key){
        return this.redisTemplate.opsForList().rightPop(key);
    }

    public Long incr(String key,Long num){
        return this.redisTemplate.opsForValue().increment(key,num);
    }
}
