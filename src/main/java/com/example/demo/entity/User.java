package com.example.demo.entity;

import java.io.Serializable;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class User implements Serializable {

    private String name;
    private Integer age;

    public String publicParm;

    public User() {
        System.out.println("我是无参构造函数");
    }


    public User(String name, Integer age) {
        System.out.println("我是有参构造函数");
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    private String privateToString(){
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", publicParm='" + publicParm + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", publicParm='" + publicParm + '\'' +
                '}';
    }

}
