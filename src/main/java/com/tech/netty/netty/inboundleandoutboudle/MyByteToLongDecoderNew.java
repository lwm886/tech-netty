package com.tech.netty.netty.inboundleandoutboudle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author lw
 * @since 2021/8/30
 */
public class MyByteToLongDecoderNew extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoderNew 被调用 可读取字节数="+in.readableBytes());
        out.add(in.readLong());
    }
}
