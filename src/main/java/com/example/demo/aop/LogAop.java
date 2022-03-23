package com.example.demo.aop;

import com.alibaba.fastjson.JSON;
import com.example.demo.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
@Aspect
@Component
@Slf4j
public class LogAop {
    @Autowired
    private LogService logService;

    @Pointcut("execution(public * com.example.demo.controller.*Controller.*(..))")
    public void point(){}

    @Before("point()")
    public void before(JoinPoint joinPoint){
        log.info("前置通知开始执行-----");
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = servletRequestAttributes.getRequest();
        logService.info("【请求时间：】" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
        logService.info("【请求URL：】" + request.getRequestURL());
        logService.info("【请求类型：】" + request.getMethod());
        logService.info("【请求IP：】" + getIpAddr(request));
        logService.info("【请求类名：】" + joinPoint.getSignature().getDeclaringTypeName());
        logService.info("【请求方法名：】" + joinPoint.getSignature().getName());
        logService.info("【请求参数：】" + JSON.toJSONString(joinPoint.getArgs()));
        log.info("前置通知结束执行-----");
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }


}
