package com.example.demo.entity;

import java.util.Random;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class SellTicket implements Runnable {
    private Ticket ticket;

    public SellTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public void run() {
        while (true){
            synchronized (this.ticket){
                final int ticketNum = this.ticket.getTicketNum();
                final int i = new Random().nextInt(8);
                if (ticketNum < i){
                    System.out.println("当前需要票数：" + i + ",票数不够，等待补充余票");
                    this.ticket.notify();
                    try {
                        this.ticket.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.ticket.sell(i);
            }
        }
    }
}
