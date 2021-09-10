package com.tech.netty.netty.inboundleandoutboudle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * encoder 传入的类型要和处理的类型Long保持一致 否则不会调用encode方法
 * @author lw
 * @since 2021/8/27
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    //编码方法
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder 被调用");
        System.out.println("msg="+msg);
        out.writeLong(msg);
    }
}
