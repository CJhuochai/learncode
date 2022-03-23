package com.example.demo.service;

import com.example.demo.entity.StrategyEnum;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-02
 **/
public interface IStrategyService {

    String talk();

    StrategyEnum getType();
}
