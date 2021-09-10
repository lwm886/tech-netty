package com.tech.netty.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author lw
 * @since 2021/8/31
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder decode 被调用");
        //需要将二进制字节码转换成协议对象
        int len = in.readInt();

        byte[] bytes = new byte[len];
        //在缓冲区读取len个字节到字节数组
        in.readBytes(bytes);

        //封装成协议包对象 传递给下一个handler进行业务处理
        MessageProtocol protocolMessage = new MessageProtocol();
        protocolMessage.setLen(len);
        protocolMessage.setContent(bytes);
        out.add(protocolMessage);
    }
}
