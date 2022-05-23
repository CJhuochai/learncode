package com.example.demo.util;

import java.util.function.Consumer;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-05
 **/
public class VUtils {

    public static Consumer<String> isTrueExce(Boolean b){
        return s -> {
            throw new RuntimeException(s);
        };
    }

    public static void main(String[] args) {
        VUtils.isTrueExce(true).accept("我是错误");
    }
}
