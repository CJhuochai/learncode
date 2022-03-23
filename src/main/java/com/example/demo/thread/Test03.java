package com.example.demo.thread;

import com.example.demo.entity.ThreadImpl;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class Test03 {

    public static void main(String[] args) {
        ThreadImpl threadImpl = new ThreadImpl();
        final Thread thread1 = new Thread(threadImpl);
        final Thread thread2 = new Thread(threadImpl);
        thread1.start();
        thread2.start();
    }
}
