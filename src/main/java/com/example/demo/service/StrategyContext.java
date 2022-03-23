package com.example.demo.service;

import com.example.demo.entity.StrategyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-02
 **/
@Component
public class StrategyContext {
    @Autowired
    private List<IStrategyService> strategyService;

    private static final Map<StrategyEnum,IStrategyService> map = new HashMap<>(2);

    @PostConstruct
    private void init(){
        strategyService.forEach(e -> map.put(e.getType(),e));
    }

    public String talk(StrategyEnum strategyState){
        return this.map.get(strategyState).talk();
    }
}
