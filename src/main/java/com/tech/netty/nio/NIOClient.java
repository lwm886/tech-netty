package com.tech.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author lw
 * @since 2021/8/2
 **/
public class NIOClient {
    public static void main(String[] args) throws Exception {
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
       //连接服务器
        if(!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("连接需要时间，客户端不会阻塞，可以做一些其他操作...");
            }
        }

        //如果连接成功，就发送数据
        String str="hello 中国";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        //将buffer数据写入通道
        socketChannel.write(byteBuffer);

        System.in.read();
    }
}
