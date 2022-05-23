package com.example.demo.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-05
 **/
public class Spring0 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MianConfig0.class);
        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
    }
}
