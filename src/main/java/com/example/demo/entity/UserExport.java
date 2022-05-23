package com.example.demo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-03
 **/
public class UserExport {

    @ExcelProperty(value = "ID")
    private String id;
    @ExcelProperty(value = "姓名")
    private String name;
    @ExcelProperty(value = "年纪")
    private Integer age;
    @DateTimeFormat("yyyyd-MM-dd HH:mm:ss")
    @ExcelProperty(value = "日期")
    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
