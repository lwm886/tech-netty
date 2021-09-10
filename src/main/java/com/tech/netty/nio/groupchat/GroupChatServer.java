package com.tech.netty.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lw
 * @since 2021/8/3
 **/
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT=6667;

    //构造器
    public GroupChatServer() {
        try {
            //创建选择器
            selector=Selector.open();
            //创建ServerSocketChannel
            serverSocketChannel = ServerSocketChannel.open();
            //绑定端口
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            //设置非阻塞
            serverSocketChannel.configureBlocking(false);
            //在选择器上注册 客户端连接事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听请求并处理
    public void listen(){
        try {
            //循环处理
            while(true){
                int selectCount = selector.select();
                //有事件处理
                if(selectCount>0){
                    //遍历得到SelectionKey集合
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while(iterator.hasNext()){
                        //取出SelectionKey
                        SelectionKey selectionKey = iterator.next();
                        //客户端连接事件
                        if(selectionKey.isAcceptable()){
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            //SocketChannel在选择器上注册数据读取事件
                            socketChannel.register(selector,SelectionKey.OP_READ);
                            //提升上线
                            System.out.println(socketChannel.getRemoteAddress()+"上线");
                        }
                        //判断为数据读取事件
                        if (selectionKey.isReadable()){
                            readData(selectionKey);
                        }
                        iterator.remove();
                    }

                }else {
                    System.out.println("等待...");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readData(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //通道数据读取到缓冲区
            int readCount = socketChannel.read(byteBuffer);
            if(readCount>0){
                String msg=new String(byteBuffer.array());
                System.out.println("from 客户端:"+msg);
                //将msg发送到选择器上其他客户端的通道（除去当前通道）
                sendInfoToOtherClients(socketChannel,msg);
            }
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress()+"离线了");
                //取消注册
                selectionKey.cancel();
                //关闭通道
                socketChannel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void sendInfoToOtherClients(SocketChannel socketChannel, String msg) throws IOException {
        System.out.println("转发消息中...");

        //遍历所有在selector上注册的channel 找出非当前SocketChannel给客户端转发消息
        for(SelectionKey selectionKey:selector.keys()){
            Channel channel = selectionKey.channel();
            if(channel instanceof SocketChannel && channel!=socketChannel){
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                ((SocketChannel) channel).write(byteBuffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
