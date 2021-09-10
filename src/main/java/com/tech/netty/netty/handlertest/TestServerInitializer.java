package com.tech.netty.netty.handlertest;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author lw
 * @since 2021/8/17
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // HttpServerCodec 是Netty提供的编解码器
        socketChannel.pipeline().addLast("MyHttpServerCodec",new HttpServerCodec())
                .addLast("",new TestServerHandler()); //在管道添加一个自定义处理器
    }
}
