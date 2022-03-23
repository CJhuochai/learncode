package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
@Service
public class LogService {

    private BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
    private Thread thread;

    public LogService() {
        this.thread = new Thread(new LogThread(),"CJ-LOG");
        this.thread.start();
    }

    public void info(String log){
        blockingQueue.offer(log);
    }

    class LogThread implements Runnable{

        @Override
        public void run() {
            while (true){
                final String poll = blockingQueue.poll();
                if (StringUtils.hasText(poll)){
                    System.out.println(poll);
                }
            }
        }
    }
}
