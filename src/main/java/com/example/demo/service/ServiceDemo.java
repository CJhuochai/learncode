package com.example.demo.service;

import com.example.demo.annotation.AsyncThream;
import org.springframework.stereotype.Service;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
@Service
public class ServiceDemo {


    @AsyncThream
    public void asyncTest1(){
        System.out.println("我是被异步执行的方法asyncTest1");
    }

    public String getText(){
        return "我是公共service";
    }
}
