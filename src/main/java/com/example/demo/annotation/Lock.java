package com.example.demo.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @program: limiting-demo
 * @description: 分布式锁
 * @author: Jian Chen
 * @create: 2021-12
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Lock {

    String key() default "";

    long waitTime() default 5L;

    long timeOut() default 10L;

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    String msg() default "请求失败!";
}
