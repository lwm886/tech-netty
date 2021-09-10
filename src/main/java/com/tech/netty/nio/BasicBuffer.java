package com.tech.netty.nio;

import java.nio.IntBuffer;

/**
 * @author lw
 * @since 2021/7/27
 **/
public class BasicBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
//        System.out.println("缓冲区的容量："+intBuffer.capacity());
//        System.out.println("缓冲区的位置："+intBuffer.position());
        for(int i=0;i<5;i++){
            intBuffer.put(i*2);
//            System.out.println("缓冲区的位置："+intBuffer.position());
        }
        //读写切换
        intBuffer.flip();
//        System.out.println("缓冲区的位置："+intBuffer.position());
//        intBuffer.position(1);
//        intBuffer.limit(2);
//        intBuffer.clear();
//        System.out.println(intBuffer.isReadOnly());
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
//            System.out.println("缓冲区的位置："+intBuffer.position());
        }
    }
}
