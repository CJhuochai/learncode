package com.example.demo.thread;
/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class Demo07 {

    public static void main(String[] args) {
        final Thread cj1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "1");
        }, "CJ-");
        final Thread cj2 = new Thread(() -> {
            try {
                cj1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "2");
        }, "CJ-");
        final Thread cj3 = new Thread(() -> {
            try {
                cj2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "3");
        }, "CJ-");
        cj1.start();
        cj2.start();
        cj3.start();

    }
}
