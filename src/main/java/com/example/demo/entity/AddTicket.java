package com.example.demo.entity;

import java.util.Random;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class AddTicket implements Runnable {
    private Ticket ticket;

    public AddTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public void run() {
        while (true){
            synchronized (this.ticket){
                final int ticketNum = this.ticket.getTicketNum();

                if (ticketNum > 10){
                    this.ticket.notify();
                    try {
                        System.out.println("当前票数：" + ticketNum + "，超过10张，暂停增加票数");
                        this.ticket.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.ticket.add(new Random().nextInt(10));
            }
        }
    }
}
