package com.tech.netty.nio;

import java.nio.ByteBuffer;

/**
 * @author lw
 * @since 2021/7/30
 **/
public class ByteBufferReadWriteReadOnly {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for(int i=0;i<64;i++){
            buffer.put((byte)i);
        }
        buffer.flip();
        for(int i=0;i<64;i++){
            System.out.println(buffer.get());
        }
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        readOnlyBuffer.put((byte)1);
    }
}
