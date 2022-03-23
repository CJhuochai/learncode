package com.example.demo.entity;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class Ticket {
    private int ticketNum;

    public Ticket(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    public void add(int num){
        this.ticketNum += num;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("添加了" + num + "张票,剩余：" + this.ticketNum);
    }

    public void sell(int num){
        if (num > this.ticketNum){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前剩余" + ticketNum + "张票,需要购票:" + num + "张，票数不够，购买失败");
            return;
        }
        this.ticketNum -= num;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("卖出了" + num + "张票,剩余：" + this.ticketNum);
    }
}
