package com.example.demo.thread;

import java.util.concurrent.*;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class Thread01 extends Thread {

    @Override
    public void run() {
        System.out.println("我只子线程：" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() ->
                {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("你好");
                }
        );

        System.out.println("主线程执行完毕");
    }
}
