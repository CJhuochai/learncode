package com.example.demo.service.impl;

import com.example.demo.entity.StrategyEnum;
import com.example.demo.service.IStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-02
 **/
@Slf4j
@Service
public class StrategyServiceImpl02 implements IStrategyService {
    @Override
    public String talk() {
        return "我是实现类02";
    }

    @Override
    public StrategyEnum getType() {
        return StrategyEnum.STRATEGY_TWO;
    }
}
