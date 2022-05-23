package com.example.demo.proxy.jdk;



import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: JDK动态代理-只能代理接口实现类,因为生成的代理类默认继承了Proxy类
 * @description:
 * @author: Jian Chen
 * @create: 2022-05
 **/
@Slf4j
public class JdkProxy implements InvocationHandler {

    private Object target;

    public JdkProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("jdk动态代理-----------------入口");
        final Object invoke = method.invoke(target, args);
        log.info("jdk动态代理-----------------出口");
        return invoke;
    }

    public static <T> T createProxyInstance(Object target, Class<T> targetInterface){
        if (!targetInterface.isInterface()){
            throw new RuntimeException("targetInterface必须是接口");
        }
        if (!targetInterface.isAssignableFrom(target.getClass())){
            throw new RuntimeException("target必须是targetInterface接口的实现类");
        }
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new JdkProxy(target));
    }
}
