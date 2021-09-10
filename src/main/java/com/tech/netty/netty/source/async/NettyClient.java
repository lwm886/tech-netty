package com.tech.netty.netty.source.async;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author lw
 * @since 2021/8/11
 **/
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //客户端需要一个事件循环组
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            // 创建客户端启动对象 并配置参数

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)  //设置线程组
                    .channel(NioSocketChannel.class)  //设置客户端通道
                    .handler(new ChannelInitializer<SocketChannel>() {  //设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("...客户端 is ready...");
            //连接服务器
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7000).sync();
            //对通道关闭事件进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }

    }
}
