package com.tech.netty.netty.inboundleandoutboudle;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author lw
 * @since 2021/8/27
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //收发数据时，如果不设置编码和解码器就需要用ByteBuf发送和接收数据，
        //收发数据时，如果设置编解码器，则编码解码器要使用相同类型和字节码进行转换
        //入站的handler进行解码
        pipeline
//                .addLast("MyByteToLongDecoder",new MyByteToLongDecoder())
                .addLast("MyByteToLongDecoderNew",new MyByteToLongDecoderNew())
                //出站的handler进行编码
                .addLast("MyLongToByteEncoder",new MyLongToByteEncoder())
                //自定义的handler处理业务逻辑
                .addLast("MyServerHandler",new MyServerHandler());
    }
}
