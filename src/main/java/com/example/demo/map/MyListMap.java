package com.example.demo.map;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-12
 **/
public class MyListMap<K,V> {

    public static void main(String[] args) {
        MyListMap<String,String> myMap = new MyListMap<>();
        myMap.put("1","我是1");
        myMap.put("2","我是2");
        myMap.put("3","我是3");
        System.out.println(myMap.get("5"));
        Integer a = 97;
        Character b = 'a';
        System.out.println(a.hashCode() + " :" + b.hashCode());
    }

    private List<MyEntry<K,V>> list = new ArrayList<>();

    public void put(K k,V v){
        list.add(new MyEntry(k,v));
    }

    public V get(K k){
        for (MyEntry m : list) {
            if (m.getK().equals(k)){
                return (V) m.getV();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "MyMap{" +
                "list=" + list +
                '}';
    }

    class MyEntry<K,V>{
        private K k;
        private V v;

        public MyEntry(K k, V v) {
            this.k = k;
            this.v = v;
        }

        public K getK() {
            return k;
        }

        public V getV() {
            return v;
        }

        @Override
        public String toString() {
            return "MyEntry{" +
                    "k=" + k +
                    ", v=" + v +
                    '}';
        }
    }

}
