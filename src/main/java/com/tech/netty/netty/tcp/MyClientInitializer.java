package com.tech.netty.netty.tcp;

import com.tech.netty.netty.inboundleandoutboudle.MyByteToLongDecoderNew;
import com.tech.netty.netty.inboundleandoutboudle.MyLongToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author lw
 * @since 2021/8/27
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyClientHandler());
    }
}
