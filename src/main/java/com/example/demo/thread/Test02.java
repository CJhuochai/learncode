package com.example.demo.thread;

import com.example.demo.entity.Singleton;
import com.example.demo.entity.User;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class Test02 {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
       /* User user = new User();
        final ArrayList<String> strings = new ArrayList<>();
        strings.add("你哦爱好");
        final Class<? extends ArrayList> aClass = strings.getClass();
        final Method add = aClass.getDeclaredMethod("add", Object.class);
        final Annotation[] annotations = add.getAnnotations();
        System.out.println(annotations.length);
        add.invoke(strings,user);
        System.out.println(strings);*/
        /*strings.forEach(e -> System.out.println(e));*/
        Singleton singleton = Singleton.getSingleton();
        System.out.println("counter1="+singleton.counter1);
        System.out.println("counter2="+singleton.counter2);
    }
}
