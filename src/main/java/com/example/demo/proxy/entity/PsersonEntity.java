package com.example.demo.proxy.entity;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-05
 **/
public class PsersonEntity {
    private String Name;
    private String say;

    public PsersonEntity(String name, String say) {
        Name = name;
        this.say = say;
    }

    public String getName() {
        return Name;
    }

    public String getSay() {
        return say;
    }
}
