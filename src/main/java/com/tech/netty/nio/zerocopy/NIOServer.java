package com.tech.netty.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author lw
 * @since 2021/8/5
 **/
public class NIOServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6667);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(inetSocketAddress);
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("客户端已连接："+socketChannel.getRemoteAddress());
            int readCount=0;
            while(-1!=readCount){
                try {
                    readCount=socketChannel.read(byteBuffer);
                    System.out.println("读取字节数："+readCount);
                } catch (IOException e) {
                    break;
                }finally {
                    //倒带 position = 0 mark作废
                    byteBuffer.rewind();
                }
            }
        }
    }
}
