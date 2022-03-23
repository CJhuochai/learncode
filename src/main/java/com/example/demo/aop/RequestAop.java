package com.example.demo.aop;

import com.example.demo.annotation.RequestLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
@Slf4j
@Aspect
@Component
public class RequestAop {

    /*@Before(value = "@annotation(com.example.demo.annotation.RequestLock)")
    public String before(JoinPoint joinPoint){
        log.info("你访问了前置通知");
        return  "你访问了后置通知";
    }

    @After(value = "@annotation(com.example.demo.annotation.RequestLock)")
    public String after(JoinPoint joinPoint){
        log.info("你访问了后置通知");
        return  "你访问了后置通知";
    }*/

    @Around(value = "@annotation(com.example.demo.annotation.RequestLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("你访问了环绕通知");
        final MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        final Method method = signature.getMethod();
        final RequestLock annotation = method.getAnnotation(RequestLock.class);
        if (!Objects.isNull(annotation)){

            return annotation.msg();
        }
        return joinPoint.proceed();
    }

    /*@AfterThrowing(value = "@annotation(com.example.demo.annotation.RequestAop)")
    public String afterThrowing(){
        log.info("你访问了异常通知");
        return  "你访问了后置通知";
    }*/
}
