package com.example.demo.condition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-02
 **/
@Configuration
public class ConditionConfig {

    @Bean
    @Conditional(value = {WinCondition.class})
    public void win(){
        System.out.println("----------------------------------windows");
    }
    @Bean
    @Conditional(value = {LinCondition.class})
    public void lin(){
        System.out.println("----------------------------------linux");
    }
}
