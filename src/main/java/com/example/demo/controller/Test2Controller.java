package com.example.demo.controller;


import com.example.demo.entity.StrategyEnum;
import com.example.demo.service.StrategyContext;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: limiting-demo
 * @description: 测试
 * @author: Jian Chen
 * @create: 2021-10
 **/
@Slf4j
@RestController
@RequestMapping("/test2")
public class Test2Controller {
    @Autowired
    private StrategyContext strategyContext;

    @GetMapping("/get-strategy")
    public Object strategy01(@RequestParam StrategyEnum state){
        return this.strategyContext.talk(state);
    }
}
