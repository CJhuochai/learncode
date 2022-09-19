package com.example.demo.liability;

/**
 * @create: 2022-09
 **/
public class Person {
    private String userName;
    private String passWord;

    public Person(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }
}
