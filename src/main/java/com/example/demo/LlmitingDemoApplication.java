package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@MapperScan("com.example.demo.dao")
@EnableTransactionManagement
public class LlmitingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LlmitingDemoApplication.class, args);
    }

}
