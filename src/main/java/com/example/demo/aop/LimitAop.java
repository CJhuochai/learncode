package com.example.demo.aop;

import com.alibaba.fastjson.JSON;
import com.example.demo.annotation.Limit;
import com.example.demo.util.RedisUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-10
 **/
@Slf4j
@Aspect
@Component
@Order(1)
public class LimitAop {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 不同的接口，不同的流量控制
     * map的key为 Limiter.key
     */

    @Around("@annotation(com.example.demo.annotation.Limit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final Method method = signature.getMethod();
        //拿到注解
        final Limit limit = method.getAnnotation(Limit.class);
        if (null != limit){
            //获取limit的key
            final String key = limit.key();
            final double v = limit.permitsPerSecond();
            //RateLimiter rateLimiter = null;
            //验证缓存是否存在key
            final Object count = this.redisUtils.get(key);
            if (count == null){
                //rateLimiter = RateLimiter.create(limit.permitsPerSecond());
                this.redisUtils.set(key,1,limit.timeout(),limit.timeunit());
                //log.info("创建了新的令牌桶:{},容量:{}",key,limit.permitsPerSecond());
            }else if (Integer.parseInt((String) count) <= v){
                this.redisUtils.incr(key,1L);
            }else{
                this.responseFail(limit.msg());
                return null;
            }
           /* rateLimiter = (RateLimiter)this.redisUtils.get(key);
            //拿令牌
            final boolean acquire = rateLimiter.tryAcquire(limit.timeout(), limit.timeunit());
            if (!acquire){
                //拿不到令牌
                log.warn("令牌桶:{},获取令牌失败！",key);
                this.responseFail(limit.msg());
                return null;
            }*/
        }
        return joinPoint.proceed();
    }

    private void responseFail(String msg) throws IOException {
        final HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Map<String,Object> resutlMap = Maps.newHashMap();
        resutlMap.put("code",500);
        resutlMap.put("msg",msg);
        final String s = JSON.toJSONString(resutlMap);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(s);
    }
}
