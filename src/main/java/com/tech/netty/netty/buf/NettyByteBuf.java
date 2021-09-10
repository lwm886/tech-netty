package com.tech.netty.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author lw
 * @since 2021/8/18
 */
public class NettyByteBuf {
    public static void main(String[] args) {
        // 创建一个ByteBuf
        //说明
        // 1. 创建对象 该对象包含一个数组arr 是一个byte[10]
        // 2. 在Netty的buffer中，不需要使用flip进行反转
        // 3. readerIndex 下一个读取的位置 writerIndex 下一个写入的位置
        // 0--readerIndex 已读取的范围
        // readerIndex--writerIndex 可读取的范围
        // writerIndex--capacity 可写入的范围
        ByteBuf buffer = Unpooled.buffer(10);
        for(int i=0;i<10;i++){
            buffer.writeByte(i);
        }
        System.out.println("buffer capacity="+buffer.capacity());
//        for(int i=0;i<buffer.capacity();i++){
//            System.out.println(buffer.getByte(i));
//        }
        for(int i=0;i<buffer.capacity();i++){
            System.out.println(buffer.readByte());
        }
        System.out.println("执行结束");
    }
}
