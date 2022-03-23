package com.example.demo.proxy;

import com.example.demo.proxy.impl.IProxyDemoImpl;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class Demo01 {
    public static void main(String[] args) {
        final IProxyDemo proxy = (IProxyDemo) new Proxy().bind(new IProxyDemoImpl());
        final String hello = proxy.hello();
        System.out.println(hello);
    }
}
