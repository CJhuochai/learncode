package com.example.demo.service;

import com.example.demo.annotation.AsyncThream;
import com.example.demo.dao.UserMapper;
import com.example.demo.entity.DbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
@Service
public class ServiceDemo {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ServiceDemo serviceDemo;


    @AsyncThream
    public void asyncTest1(){
        System.out.println("我是被异步执行的方法asyncTest1");
    }

    public String getText(){
        return "我是公共service";
    }

    //@Transactional
    public void a(){
        final DbUser dbUser = new DbUser();
        dbUser.setId(1L);
        dbUser.setUserName("陈健1");
        dbUser.setAccount("aaaa1");
        dbUser.setPassword("1231");
        this.userMapper.insert(dbUser);
        //this.serviceDemo.b();
        this.b();

    }

    @Transactional
    public void b(){
        final DbUser dbUser = new DbUser();
        dbUser.setId(2L);
        dbUser.setUserName("陈健2");
        dbUser.setAccount("aaaa2");
        dbUser.setPassword("1232");
        this.userMapper.insert(dbUser);
        throw new RuntimeException("我报错勒");
    }
}
