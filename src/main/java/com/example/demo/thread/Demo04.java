package com.example.demo.thread;

import com.example.demo.entity.Deadlock;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class Demo04 {
    public static void main(String[] args) {
        Deadlock deadlock = new Deadlock();
        final Thread thread1 = new Thread(deadlock);
        final Thread thread2 = new Thread(deadlock);
        thread1.start();
        thread2.start();
        System.out.println("主线程执行完毕");
    }
}
