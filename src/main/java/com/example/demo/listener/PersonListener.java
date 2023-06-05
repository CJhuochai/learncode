package com.example.demo.listener;

import com.example.demo.dao.UserMapper;
import com.example.demo.entity.DbUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-03
 **/
@Slf4j
@Component
public class PersonListener {
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @EventListener
    public void sendMsg(PersonEvent event){
        final int age = event.getAge();
        final String name = event.getName();
        final DbUser dbUser = new DbUser();
        dbUser.setId(321l);
        dbUser.setAccount(name);
        this.userMapper.insert(dbUser);
        //throw new RuntimeException("我报错勒");
    }

    @EventListener
    public void sendCoupons(PersonEvent event){
        System.out.println("发送消费券");
    }
}
