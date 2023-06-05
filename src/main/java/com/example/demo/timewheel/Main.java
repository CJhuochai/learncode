package com.example.demo.timewheel;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: Jian Chen
 * @create: 2023-03
 **/
public class Main {
    //驱动时间轮向前的线程
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);
    public static  SystemTimer timer = new SystemTimer("test",1000L,5,System.currentTimeMillis());


    public static void runTask() throws Exception {
        for(int i = 0;i < 10000;i+= 1000) {
            final long l = RandomUtil.randomLong(1000, 2000);
            final long l1 = i + l;
            // 添加任务，每个任务间隔1s
            timer.add(new TimeTask(l1) {
                @Override
                public void run() {
                    System.out.println("运行testTask的时间: " + DateUtil.date(l1));
                }
            });
        }
    }

    public static void main(String[] args) throws Exception {
        runTask();

        executorService.submit(() -> {
            while(true) {
                try {
                    // 驱动时间轮线程间隔0.2s驱动
                    timer.advanceClock(200L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        Thread.sleep(1000000);
        timer.shutdown();
        executorService.shutdown();
    }
}
