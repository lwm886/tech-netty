package com.tech.netty.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author lw
 * @since 2021/8/5
 **/
public class NIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",6667));
        String fileName="1.txt";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        long start=System.currentTimeMillis();
        //在linux操作系统下 transferTo 传输大文件 调用一次即可
        //在windows操作系统下 transferTo 一次最多传输8M 如果文件大于8M 需要分批次多次调用传输
        //transferTo 底层使用了零拷贝技术
        long l = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的总字节数："+l+" 耗时："+(System.currentTimeMillis()-start));
        fileChannel.close();
    }
}
