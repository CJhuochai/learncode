package com.example.demo.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class Demo {

    public static void main(String[] args) throws Exception {
        final CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("运行再单独的一个线程中");
            return "我是返回值";
        }).thenApply(first -> {
            System.out.println(first);
            return first + "1";
        })
                .thenApply(two -> {
                    System.out.println(two);
                    return two + "转发";
                });
        //System.out.println(completableFuture.get());
        System.out.println("我是主线程");
    }


    public int search(int[] nums, int target) {
        int left = 0,right = nums.length - 1;
        //左闭右开[ )
        while (left < right){
            int midd = left + ((right - left)/2);
            if (target == nums[midd]){
                return midd;
            }
            if (target < nums[midd]){
                right = midd;
            }
            if (target > nums[midd]){
                left = midd+1;
            }
        }
        return -1;
    }

    private int[] array = null;
    //array =  new int[10000];
    public void add(int key) {
        if (!this.contains(key)){
            this.array[this.array.length+1] = key;
        }
    }

    public void remove(int key) {
        if (this.contains(key)){
            int index = getIndex(key);
            if (index != -1){
                for (int i = index ;index < array.length;index++){
                    array[index] = array[index+1];
                }
            }
        }
    }

    public boolean contains(int key) {
       return this.getIndex(key) != -1 ? true : false;
    }

    private int getIndex(int key){
        int left = 0,right = this.array.length - 1;
        while (left < right){
            int mid = left + ((right - left) >> 2);
            if (key == this.array[mid]){
                return mid;
            }
            if (key < this.array[mid]){
                right = mid;
            }
            if (key > this.array[mid]){
                left = mid;
            }
        }
        return -1;
    }
}
