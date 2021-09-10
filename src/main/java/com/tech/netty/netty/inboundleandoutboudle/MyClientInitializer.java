package com.tech.netty.netty.inboundleandoutboudle;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author lw
 * @since 2021/8/27
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //加入一个出站的handler 对数据进行编码
        pipeline.addLast(new MyLongToByteEncoder())
                //加入一个入站的解码器
//                .addLast(new MyByteToLongDecoder())
                .addLast(new MyByteToLongDecoderNew())
                //加入一个自定义的handler处理业务
                .addLast(new MyClientHandler());
    }
}
