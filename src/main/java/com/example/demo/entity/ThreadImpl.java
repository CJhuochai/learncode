package com.example.demo.entity;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class ThreadImpl implements Runnable {
    private int i = 100;
    @Override
    public void run() {
        while (true){
            if (i > 0){
                try {
                    Thread.sleep(30);
                    synchronized (this){
                        if (i >0){
                            i--;
                        }
                        System.out.println(Thread.currentThread().getName() + ":i=" + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                break;
            }
        }
    }
}
