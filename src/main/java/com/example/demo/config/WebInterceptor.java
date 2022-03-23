package com.example.demo.config;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-02
 **/
@Component
public class WebInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.responseFail("被拦截了");
        return false;
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
