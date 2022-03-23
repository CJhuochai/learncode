package com.example.demo.thread;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-12
 **/
public class Demo10 {
    public static void main(String[] args) {
        LinkCache<String,String> cache = new LinkCache<>(3);
        cache.put("a","a");
        cache.put("b","b");
        cache.put("c","c");


        cache.forEach((k,v) -> System.out.println("k:" + k + ",v:" + v));
        System.out.println("----------------------------------");
        cache.get("a");
        cache.put("d","d");
        cache.forEach((k,v) -> System.out.println("k:" + k + ",v:" + v));
    }
}



class LinkCache<K,V> extends LinkedHashMap<K,V> {
    private int cap;

    public LinkCache(int cap) {
        super(cap, 0.75f, true);
        this.cap = cap;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.size() > this.cap;
    }



}
