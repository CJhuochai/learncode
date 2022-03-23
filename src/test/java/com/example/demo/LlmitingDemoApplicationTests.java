package com.example.demo;


import com.alibaba.fastjson.JSON;
import com.example.demo.listener.PersonEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SimpleThreadPoolTaskExecutor;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.function.*;

@Slf4j
@SpringBootTest
class LlmitingDemoApplicationTests {

    @Autowired
    private RedissonClient redissonClient;


    @Test
    void contextLoads() throws InterruptedException {
        RLock rLock = redissonClient.getLock("abc");
        boolean lock = rLock.tryLock(10, TimeUnit.SECONDS);
        System.out.println("thread1 lock."+ lock);
        final Thread thread = new Thread(() -> {
            System.out.println("thread2 start.");
            RLock rLock1 = redissonClient.getLock("abc");
            boolean lock1 = false;
            try {
                lock1 = rLock1.tryLock(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread2 lock:" + lock1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (lock1){
                rLock1.unlock();
                System.out.println("thread2 unlock.");
            }

        });
        thread.start();
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rLock.unlock();
        System.out.println("thread1 unlock.");
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //设置读取值
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong("a");
        RBucket<String> kv2 = redissonClient.getBucket("1");
        System.out.println(kv2.get());
    }
   @Test
   public void test1(){
       System.out.println("你好");
   }

   @Autowired
   private WebApplicationContext webApplicationContext;

    @Test
    public void listener(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        PersonEvent person = new PersonEvent("person",1,"陈健");
        executor.execute(() -> webApplicationContext.publishEvent(person));
        System.out.println("主线程组合完成");
    }

    @Test
    public void dateTest(){
        LocalDate localDate = LocalDate.now();
        log.info("localDate-now:{}",localDate);
        log.info("localDate-year:{}",localDate.getYear());
        log.info("localDate-month:{}",localDate.getMonth().name());
        log.info("localDate-monthDay:{}",localDate.getDayOfMonth());
        log.info("localDate-dayWeek:{}",localDate.getDayOfWeek().name());
        log.info("localDate-dayYear:{}",localDate.getDayOfYear());
    }

    @Test
    public void preTest() {
        String token1 = "68eb5da2-7f74-4a54-872b-eda284163045:c2269b3c-c3b8-49a9-9549-738c4f192912";
        final String s2 = Base64Utils.encodeToString(token1.getBytes());
        System.out.println(s2);
    }


    private List<Boolean> pre(List<String> a, Predicate<String> predicate){
        List<Boolean> rs = new ArrayList<>(a.size());
        for (String b: a) {
            if (predicate.test(b)){
                rs.add(true);
            }else {
                rs.add(false);
            }
        }
        return rs;
    }
    @Test
    public void biFunctionTest(){
        final List<String> list = Arrays.asList("aaa", "bb", "vv", "dd");
        final Integer bb = this.getStr("bb", list, (a, b) -> {
            if (b.contains(a)) {
                return 1;
            } else {
                return 0;
            }
        });
        System.out.println(bb);
    }
    private Integer getStr(String target, List<String> datas, BiFunction<String,List<String>,Integer> biFunction){
        return biFunction.apply(target,datas) + 1;
    }

    public static void main(String[] args) {
        String token1 = "68eb5da2-7f74-4a54-872b-eda284163045:c2269b3c-c3b8-49a9-9549-738c4f192912";
        final String s2 = Base64Utils.encodeToString(token1.getBytes());
        System.out.println(s2);
    }
}
