package com.example.demo.spring;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-06
 **/
public class DemoClass {
    private boolean a;
    private String b;

    public DemoClass(String b) {
        this.b = b;
    }

    public boolean isA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public static void main(String[] args) {
        final DemoClass nihao = new DemoClass("nihao");
        final Demo2 demo2 = new Demo2(nihao.isA(), nihao.getB());
        System.out.println(demo2);
    }


    public static class Demo2{
        private Boolean a;
        private String name;

        public Demo2(Boolean a, String name) {
            this.a = a;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Demo2{" +
                    "a=" + a +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
