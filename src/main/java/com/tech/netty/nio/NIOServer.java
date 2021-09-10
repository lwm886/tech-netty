package com.tech.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lw
 * @since 2021/8/2
 **/
public class NIOServer {
    public static void main(String[] args) throws Exception {
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //创建选择器
        Selector selector = Selector.open();
        //绑定端口 在服务端进行监听
        serverSocketChannel.bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //在选择器上注册监听事件 事件类型为客户端连接
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接及读取客户端发送的数据
        while(true){
            //监听事件1秒钟，如果没有事件发生则重新监听
            if(selector.select(1000)==0){
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            //表示已获取到监听的事件，获取selectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //进行遍历
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //说明SelectionKey对应的通道上发生了客户端连接事件
                if(selectionKey.isAcceptable()){
                    //给客户端创建一个SocketChannel进行通信
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("有客户端连接 socketChannel hashCode="+socketChannel.hashCode());
                    //SocketChannel设置非阻塞
                    socketChannel.configureBlocking(false);
                    //在选择器上注册读取事件，并关联一个缓冲区用于数据读写
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }else if(selectionKey.isReadable()){ //说明SelectionKey对应的通道上发生了读取事件
                    //通过SelectionKey反向获取通道
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    //获取通道关联的缓冲区
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();

                    channel.read(byteBuffer);
                    System.out.println("from 客户端："+new String(byteBuffer.array()));
                }
                //手动移除SelectionKey 防止重复操作
                iterator.remove();
            }
        }
    }
}
