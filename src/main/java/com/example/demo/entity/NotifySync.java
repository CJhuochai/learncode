package com.example.demo.entity;

import lombok.SneakyThrows;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class NotifySync implements Runnable {
    @Override
    public void run() {
        synchronized (this) {
            try {
                System.out.println("我是第一个输出");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
