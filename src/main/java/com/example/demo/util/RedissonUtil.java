package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-12
 **/
@Slf4j
@Component
public class RedissonUtil {

    @Autowired
    private RedissonClient redissonClient;


    public RLock getLock(String key){
        final RLock lock = redissonClient.getLock(key);
        lock.lock();
        return lock;
    }

    public void lock(String key, Long time, TimeUnit timeUnit){
        final RLock lock = redissonClient.getLock(key);
        lock.lock(time,timeUnit);
    }

    public void unLock(String key){
        final RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }

    public Boolean tryLock(String key,Long waitTime,Long timeOut,TimeUnit timeUnit) throws InterruptedException {
        final RLock lock = redissonClient.getLock(key);
        return lock.tryLock(waitTime, timeOut, timeUnit);
    }
}
