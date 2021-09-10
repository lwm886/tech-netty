package com.tech.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author lw
 * @since 2021/7/30
 **/
public class BufferArray {
    public static void main(String[] args) throws IOException {
        //初始化ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        serverSocketChannel.bind(inetSocketAddress);

        //初始化缓冲区
        ByteBuffer[] buffers=new ByteBuffer[2];
        buffers[0]=ByteBuffer.allocate(5);
        buffers[1]=ByteBuffer.allocate(3);

        //等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        int messages=8;
        //循环读取 写入客户端
        while(true){
            int readCount=0;
            //读取客户端数据 只到8个字节
            while(readCount<messages){
                long read = socketChannel.read(buffers);
                readCount+=read;
                System.out.println("readCount="+readCount);
                //打印buffer数组
                Arrays.asList(buffers).stream().map(byteBuffer -> "position="+byteBuffer.position()+",limit="+byteBuffer.limit()).forEach(System.out::println);
            }
            //读写转换
            Arrays.asList(buffers).forEach(byteBuffer -> byteBuffer.flip());

            int writeCount=0;
            //将读取到的数据写入到通道
            while(writeCount<messages){
                long write = socketChannel.write(buffers);
                writeCount+=write;
            }

            //打印读写字节数
            System.out.println("readCount="+readCount+"writeCount="+writeCount+"messageCount="+messages);

            //重置缓冲区状态
            Arrays.asList(buffers).forEach(byteBuffer -> byteBuffer.clear());
        }

    }
}
