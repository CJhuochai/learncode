package com.example.demo.proxy;

import com.example.demo.proxy.cglib.CglibProxy;
import com.example.demo.proxy.entity.IPerson;
import com.example.demo.proxy.entity.PersonA;
import com.example.demo.proxy.entity.PersonB;
import com.example.demo.proxy.entity.PsersonEntity;
import com.example.demo.proxy.jdk.JdkProxy;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Dispatcher;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: Jian Chen
 * @create: 2022-05
 **/
public class TestMain {

    public static void main(String[] args) {
        final PsersonEntity person = new PsersonEntity("陈健", "hello word!");
        final PersonA personA = new PersonA(person);
        final IPerson jdkProxy1 = JdkProxy.createProxyInstance(new PersonA(person), IPerson.class);
        final IPerson jdkProxy2 = JdkProxy.createProxyInstance(new PersonB(person), IPerson.class);
        jdkProxy1.name();
        jdkProxy1.say();
        jdkProxy2.name();
        jdkProxy2.say();
        Callback[] callbacks = {
                (MethodInterceptor) (o, method, objects, methodProxy) -> null,
                (Dispatcher) () -> null};
        final PersonA cglibProxy = CglibProxy.createCglibProxy(personA,callbacks);
        cglibProxy.say();
        cglibProxy.name();
    }
}
