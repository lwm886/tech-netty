package com.tech.netty.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author lw
 * @since 2021/8/19
 */
public class GroupChatServer {

    //监听端口
    private int port;

    public GroupChatServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {

        //创建两个线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128) //服务端通道连接队列长度
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //客户端通道保持活动状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { //设置客户端通道绑定管道的处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //获取管道
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("StringDecoder", new StringDecoder()) //向管道加入解码器
                                    .addLast("StringEncoder", new StringEncoder()) //向管道加入编码器
                                    .addLast(new GroupChatServerHandler()); //向管道加入自定义处理器
                        }
                    });
            //绑定端口启动服务器（同步运行）
            System.out.println("netty 服务器启动");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //向异步执行结果添加监听器监听关闭事件（同步运行）
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChatServer(7000).run();
    }
}
