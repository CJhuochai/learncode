package com.example.demo.liability;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @create: 2022-09
 **/
@Slf4j
public class NameHandler extends Handler<Person> {
    @Override
    public void doHandler(Person person) {
        log.info("开始执行：{}",this.getClass().getName());
        if (StringUtil.isBlank(person.getUserName()) || StringUtil.isBlank(person.getPassWord())){
            log.error("用户名/密码错误！");
            return;
        }
        log.info("校验通过:{}",this.getClass().getName());
        if (null != super.handler){
            super.handler.doHandler(person);
        }
    }
}
