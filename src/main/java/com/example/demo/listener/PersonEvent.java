package com.example.demo.listener;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-03
 **/
@Getter
@Setter
public class PersonEvent extends ApplicationEvent {
    private int age;
    private String name;
    public PersonEvent(Object source) {
        super(source);
    }

    public PersonEvent(Object source, int age, String name) {
        super(source);
        this.age = age;
        this.name = name;
    }
}
