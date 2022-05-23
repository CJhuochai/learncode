package com.example.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-03
 **/
@Slf4j
@Component
public class PersonListener {

    @EventListener
    public void sendMsg(PersonEvent event){
        System.out.println("发送消息");
    }

    @EventListener
    public void sendCoupons(PersonEvent event){
        System.out.println("发送消费券");
    }
}
