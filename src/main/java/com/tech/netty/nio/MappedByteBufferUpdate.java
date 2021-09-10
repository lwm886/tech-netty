package com.tech.netty.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lw
 * @since 2021/7/30
 **/
public class MappedByteBufferUpdate {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        //获取MappedByteBuffer
        // 第一个参数：读写模式
        // 第二个参数: 从哪个字节开始可以读写，下标从0开始
        // 第三个参数： 允许读写的字节数 （示例是从下标为0的字节可以读写，允许连续操作5个字节）
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0,(byte)'A');
        mappedByteBuffer.put(1,(byte)'B');
        randomAccessFile.close();
        System.out.println("操作完成~~");
    }

}
