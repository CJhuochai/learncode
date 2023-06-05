package com.example.demo.service;

import com.example.demo.annotation.AsyncThream;
import com.example.demo.dao.UserMapper;
import com.example.demo.entity.DbUser;
import com.example.demo.listener.PersonEvent;
import com.example.demo.spring.AsynTaskExecutor;
import com.example.demo.spring.AsynTaskExecutor2;
import com.example.demo.util.TransactionalSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import sun.dc.pr.PRError;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    private WebApplicationContext webApplicationContext;


    @AsyncThream
    public void asyncTest1(){
        System.out.println("我是被异步执行的方法asyncTest1");
    }

    public String getText(){
        return "我是公共service";
    }

    @Transactional
    public void a(){
        final DbUser dbUser = new DbUser();
        dbUser.setId(1L);
        dbUser.setAccount("aaaa1");
        this.userMapper.insert(dbUser);
        PersonEvent person = new PersonEvent("person",1,"陈健");
        webApplicationContext.publishEvent(person);

        //this.serviceDemo.b
        //this.b();
        /*transactionalSupport.execTaskWithRequiresNew((Runnable) () -> {
            final DbUser dbUser1 = new DbUser();
            dbUser1.setId(2L);
            dbUser1.setAccount("aaaa2");
            userMapper.insert(dbUser1);
            throw new RuntimeException("我报错勒");
        });*/
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW,isolation= Isolation.READ_COMMITTED)
    public void b(){
        final DbUser dbUser1 = new DbUser();
        dbUser1.setId(2L);
        dbUser1.setAccount("aaaa2");
        this.userMapper.insert(dbUser1);
        throw new RuntimeException("我报错勒");
    }

    @Autowired
    private AsynTaskExecutor asynTaskExecutor;

    @Autowired
    private AsynTaskExecutor2 asynTaskExecutor2;
    public void bb() throws Exception {
        DbUser dbUser1 = new DbUser();
        dbUser1.setId(1l);
        dbUser1.setAccount("111");
        DbUser dbUser2 = new DbUser();
        dbUser2.setId(2l);
        dbUser2.setAccount("222");
        DbUser dbUser3 = new DbUser();
        dbUser3.setId(3l);
        dbUser3.setAccount("333");
        List<Runnable> tasks = new ArrayList<>();
        Runnable runnable1 = () ->this.userMapper.insert(dbUser1);
        Runnable runnable2 = () ->this.userMapper.insert(dbUser2);
        Runnable runnable3 = () -> {
           int i = 1 / 0;
            this.userMapper.insert(dbUser3);
        };
        tasks.add(runnable3);
        tasks.add(runnable2);
        asynTaskExecutor2.doTask(tasks);
        runnable1.run();
    }
}
