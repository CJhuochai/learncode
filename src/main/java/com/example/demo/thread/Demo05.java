package com.example.demo.thread;

import com.example.demo.entity.NotifySync;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class Demo05 {

    public static void main(String[] args) {
        NotifySync notifySync = new NotifySync();
        final Thread thread = new Thread(notifySync);
        thread.start();
        System.out.println("我是主线程");
        synchronized (notifySync){
            notifySync.notify();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我是第二个输出");
        }
    }
}
