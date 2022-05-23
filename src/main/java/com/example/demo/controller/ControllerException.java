package com.example.demo.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-05
 **/
@Slf4j
@RestControllerAdvice
public class ControllerException {

    @ExceptionHandler(RuntimeException.class)
    public Object RunExp(RuntimeException re) {
        log.error(re.getMessage());
        return JSONUtil.toJsonStr(MapUtil
                .builder()
                .put("code", HttpStatus.HTTP_INTERNAL_ERROR)
                .put("msg", re.getMessage())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public Object Exp(Exception e) {
        log.error(e.getMessage());
        return JSONUtil.toJsonStr(MapUtil
                .builder()
                .put("code", HttpStatus.HTTP_INTERNAL_ERROR)
                .put("msg", e.getMessage())
                .build());
    }
}
