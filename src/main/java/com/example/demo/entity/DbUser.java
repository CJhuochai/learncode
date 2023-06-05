package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-12
 **/
@TableName("sys_user")
public class DbUser implements Serializable {
    private Long id;
    private String account;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
