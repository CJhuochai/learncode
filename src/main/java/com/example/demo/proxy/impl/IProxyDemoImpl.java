package com.example.demo.proxy.impl;

import com.example.demo.proxy.IProxyDemo;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
@Slf4j
public class IProxyDemoImpl implements IProxyDemo {
    @Override
    public String hello() {
        log.info("hello word！");
        return "你好啊";
    }
}
