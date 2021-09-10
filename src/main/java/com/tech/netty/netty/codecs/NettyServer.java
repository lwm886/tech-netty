package com.tech.netty.netty.codecs;


import com.tech.netty.netty.codec.StudentPOJO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @author lw
 * @since 2021/8/11
 **/
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

        //1 创建两个线程组bossGroup workerGroup
        //2 bossGroup处理客户端连接请求 workerGroup处理客户端数据读写请求
        //3 他们都是事件循环组 默认含有CPU核数*2个事件循环NioEventLoop
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try {
            //创建服务器端启动对象，进行参数配置
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)  //设置两个线程组
                    .channel(NioServerSocketChannel.class)  //设置服务器端通道为NioServerSocketChannel
                    .option(ChannelOption.SO_BACKLOG, 128) //设置连接队列允许的连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置连接连接状态为活动连接
                    .childHandler(new ChannelInitializer<SocketChannel>() { //给workerGroup NioEventLoop通过管道添加处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //加入protoBuf的解码器
                            socketChannel.pipeline().addLast("decoder",new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("...服务器 is ready...");
            //启动服务器并绑定一个端口
            System.out.println("ready");
            ChannelFuture channelFuture = serverBootstrap.bind(7001).sync();
            System.out.println("ok");
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        System.out.println("服务启动成功");
                    }else{
                        System.out.println("服务启动失败");
                    }
                }
            });
            //对通道的关闭事件进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
