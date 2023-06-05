package com.example.demo.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2023-03
 **/
public enum IEnum {
    NETWORK(),SCHOOL_LEVEL(Arrays.asList("HOS","VICE HOS")), DIVISION(Collections.singletonList("HOD")), DIVISION_SUBJECT(Arrays.asList("HOD","CHAIR"));

    private List<String> role;

    IEnum() {
    }

    IEnum(List<String> role) {
        this.role = role;
    }

    public List<String> getRole() {
        return role;
    }


    public static void main(String[] args) {
        System.out.println(IEnum.DIVISION_SUBJECT.getRole());
    }
}
