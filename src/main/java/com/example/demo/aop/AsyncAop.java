package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
@Slf4j
@Aspect
@Component
public class AsyncAop {

    @Around(value = "@annotation(com.example.demo.annotation.AsyncThream)")
    public void around(ProceedingJoinPoint joinPoint){
        log.info("环绕通知开始执行-----");
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }).start();
        log.info("环绕通知结束执行-----");
    }
}
