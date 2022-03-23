package com.example.demo.entity;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class Deadlock implements Runnable {
    private int i = 0;
    private Object objectLock = new Object();
    @Override
    public void run() {
            while (true){
                i++;
                if (i % 2==0){
                    synchronized (this){
                        this.aMethod();
                    }
                }else {
                    synchronized (objectLock){
                        this.bMethod();
                    }
                }
            }
    }

    private void aMethod(){
        synchronized (objectLock){
            System.out.println("我是A方法，我需要获取objectLock锁");
        }
    }

    private synchronized void bMethod(){
        System.out.println("我是A方法，我需要获取this锁");
    }
}
