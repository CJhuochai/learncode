package com.example.demo.proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @program: CGLIB动态代理
 * @description:
 * @author: Jian Chen
 * @create: 2022-05
 **/
@Slf4j
public class CglibProxy implements MethodInterceptor {
    private Object target;



    public CglibProxy(Object target) {
        this.target = target;
    }

    /**
     *
     * @param o 代理对象
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("Cglib动态代理-----------------入口");
        final Object invoke = method.invoke(target, objects);
        log.info("Cglib动态代理-----------------出口,返回值：{}",invoke);
        return invoke;
    }

    public static <T> T createCglibProxy(T target, Callback[] callbacks){
        Enhancer enhancer = new Enhancer();
        if (target.getClass().isInterface()){
            enhancer.setInterfaces(target.getClass().getInterfaces());
        }else {
            enhancer.setSuperclass(target.getClass());
        }
        enhancer.setCallbacks(callbacks);
        return (T) enhancer.create();
    }
}
