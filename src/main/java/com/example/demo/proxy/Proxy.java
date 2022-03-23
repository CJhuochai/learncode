package com.example.demo.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
@Slf4j
public class Proxy implements InvocationHandler {

    private Object target;


    public Object bind(Object object){
        this.target = object;
        return java.lang.reflect.Proxy.newProxyInstance(object.getClass().getClassLoader(),object.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("代理之前-----proxy：{}",proxy.getClass().getName());
        final Object invoke = method.invoke(target, args);
        log.info("代理之后-----target：{}",target.getClass().getName());
        log.info("代理之后-----obj：{}",invoke.getClass().getName());
        return invoke;
    }
}
