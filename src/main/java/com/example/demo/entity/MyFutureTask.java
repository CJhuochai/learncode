package com.example.demo.entity;

import java.util.concurrent.locks.LockSupport;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class MyFutureTask<T> implements Runnable {
    private MyCallable<T> myCallable;
    //private Object locl = new Object();
    private T result;
    private Thread currThread;

    public MyFutureTask(MyCallable myCallable) {
        this.myCallable = myCallable;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = this.myCallable.call();
        System.out.println("子线程执行完毕：" + result);
        LockSupport.unpark(this.currThread);
        /*synchronized (locl){
            locl.notify();
        }*/
    }

    public T get(){
        /*synchronized (locl){
            try {
                locl.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        this.currThread = Thread.currentThread();
        LockSupport.park();
        return result;
    }
}
