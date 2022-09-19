package com.example.demo.liability;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @create: 2022-09
 **/
@Slf4j
public class PassWordHandler extends Handler<Person> {
    @Override
    public void doHandler(Person person) {
        log.info("开始执行：{}",this.getClass().getName());
        if (!person.getPassWord().contains("chen")){
            log.error("密码不包含指定字符！");
            return;
        }
        log.info("校验通过:{}",this.getClass().getName());
        if (null != super.handler){
            super.handler.doHandler(person);
        }
    }
}
