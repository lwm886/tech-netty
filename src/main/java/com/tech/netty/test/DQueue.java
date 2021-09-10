package com.tech.netty.test;

import java.util.concurrent.DelayQueue;

/**
 * @author lw
 * @since 2021/9/6
 */
public class DQueue {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<Item> delayQueue=new DelayQueue<Item>();
        Item item1 = new Item(1, 3000);
        Item item2 = new Item(2, 6000);
        Item item3= new Item(3, 9000);
        delayQueue.put(item2);
        delayQueue.put(item1);
        delayQueue.put(item3);
        int size = delayQueue.size();
        for (int i = 0; i <size ; i++) {
            System.out.println(delayQueue.take());
        }
    }
}
