package com.example.demo.thread;

import com.example.demo.entity.MyCallable;
import com.example.demo.entity.MyFutureTask;

import java.util.concurrent.FutureTask;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class Demo08 {

    public static void main(String[] args) {
        MyFutureTask<Integer> futureTask =  new MyFutureTask<>((MyCallable<Integer>) () -> 1);
        final Thread thread = new Thread(futureTask);
        thread.start();
        final Integer integer = futureTask.get();
        System.out.println("主线程获取子线程返回结果:" + integer);
    }
}
