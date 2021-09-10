package com.tech.netty.nio;

import java.nio.ByteBuffer;

/**
 * @author lw
 * @since 2021/7/30
 **/
public class ByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.putInt(100);
        byteBuffer.putLong(6L);
        byteBuffer.putChar('中');
        byteBuffer.putShort((short)1);
        byteBuffer.flip();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
    }
}
