package com.example.demo.aop;

import com.alibaba.fastjson.JSON;
import com.example.demo.annotation.Lock;
import com.example.demo.util.RedissonUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-12
 **/
@Slf4j
@Aspect
@Component
public class LockAop {
    @Autowired
    private RedissonUtil redissonUtil;

    @Pointcut("@annotation(com.example.demo.annotation.Lock)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        final MethodSignature signature = (MethodSignature) point.getSignature();
        final Method method = signature.getMethod();
        final Lock lock = method.getAnnotation(Lock.class);
        String key = StringUtils.hasText(lock.key()) ? lock.key() + "@" + method.getDeclaringClass().getName() + "." + method.getName() : method.getDeclaringClass().getName() + "." + method.getName();
        long waitTime = lock.waitTime();
        long timeOut = lock.timeOut();
        TimeUnit timeUnit = lock.timeUnit();
        boolean b = false;
        try {
            b = redissonUtil.tryLock(key, waitTime, timeOut, timeUnit);
            if (!b) {
                this.responseFail(lock.msg());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (b) {
                redissonUtil.unLock(key);
                log.info("解锁key：{}", key);
            }
        }
        return point.proceed();
    }

    private void responseFail(String msg) throws IOException {
        final HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Map<String, Object> resutlMap = Maps.newHashMap();
        resutlMap.put("code", 500);
        resutlMap.put("msg", msg);
        final String s = JSON.toJSONString(resutlMap);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(s);
    }
}
