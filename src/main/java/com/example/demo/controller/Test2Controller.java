package com.example.demo.controller;


import com.example.demo.entity.StrategyEnum;
import com.example.demo.service.StrategyContext;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @program: limiting-demo
 * @description: 测试
 * @author: Jian Chen
 * @create: 2021-10
 **/
@Slf4j
@RestController
@RequestMapping("/test2")
public class Test2Controller {
    @Autowired
    private StrategyContext strategyContext;

    @GetMapping("/get-strategy")
    public Object strategy01(@RequestParam StrategyEnum state){
        return this.strategyContext.talk(state);
    }


    public static void main(String[] args) {
        Product prod1 = new Product(1L, 1, new BigDecimal("15.5"), "面包", "零食");
        Product prod2 = new Product(2L, 2, new BigDecimal("20"), "饼干", "零食");
        Product prod3 = new Product(3L, 3, new BigDecimal("30"), "月饼", "零食");
        Product prod4 = new Product(4L, 3, new BigDecimal("10"), "青岛啤酒", "啤酒");
        Product prod5 = new Product(5L, 10, new BigDecimal("15"), "百威啤酒", "啤酒");
        List<Product> prodList = Lists.newArrayList(prod1, prod2, prod3, prod4, prod5);
        final Map<String, List<Product>> collect = prodList.stream().collect(Collectors.groupingBy(Product::getCategory));
        //System.out.println(collect);
        final Map<String, Map<Integer, Set<Product>>> collect1 = prodList.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.groupingBy(Product::getNum, Collectors.toSet())));
        //System.out.println(collect1);
        final List<Product> collect2 = prodList.stream().sorted(Comparator.comparingInt(Product::getNum).reversed()).collect(Collectors.toList());
        //System.out.println(prodList);
        //System.out.println(collect2);
        prodList.sort(Comparator.comparingInt(Product::getNum).reversed());
        //System.out.println(prodList);

       //pointLength();

        /*List<String> strings = new ArrayList<>();
        strings.add(null);
        Set set = new TreeSet();
        set.addAll(strings);*/

        String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        System.out.println(Pattern.matches(reg,"1111qq"));

    }
    private static boolean pointLength() {
        final String[] split = "1.1".split("\\.");
        if (split.length == 1) {
            return true;
        }
        return split[1].length() <= 2;
    }

}

class Product{
    private Long id;
    private Integer num;
    private BigDecimal price;
    private String name;
    private String category;
    Product(Long id, Integer num, BigDecimal price, String name, String category) {
        this.id = id;
        this.num = num;
        this.price = price;
        this.name = name;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public Integer getNum() {
        return num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", num=" + num +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
