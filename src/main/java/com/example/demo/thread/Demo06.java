package com.example.demo.thread;

import com.example.demo.entity.AddTicket;
import com.example.demo.entity.SellTicket;
import com.example.demo.entity.Ticket;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class Demo06 {

    public static void main(String[] args) {
        Ticket ticket = new Ticket(7);
        AddTicket addTicket = new AddTicket(ticket);
        SellTicket sellTicket = new SellTicket(ticket);
        new Thread(addTicket).start();
        new Thread(sellTicket).start();

    }

}
