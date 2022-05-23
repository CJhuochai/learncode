package com.example.demo.proxy.entity;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-05
 **/
@Slf4j
public class PersonB implements IPerson {
    private PsersonEntity pserson;

    public PersonB(PsersonEntity pserson) {
        this.pserson = pserson;
    }

    @Override
    public void say() {
        log.info("{}说了{}",this.getClass().getName(),pserson.getSay());
    }

    @Override
    public String name() {
        return pserson.getName();
    }
}
