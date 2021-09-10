package com.tech.netty.test;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author lw
 * @since 2021/9/6
 */
public class Item implements Delayed {

    private int num;
    private long time;

    public Item(int num, long delay) {
        this.num = num;
        this.time = delay+System.currentTimeMillis();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return time-System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        Item o1 = (Item) o;
        long diff=time-o1.time;
        if(diff>0){
            return -1;
        }else{
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "num=" + num +
                ", time=" + time +
                '}';
    }
}
