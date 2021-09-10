package com.tech.netty.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author lw
 * @since 2021/8/31
 */
//处理业务的handler
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //接收到数据并处理
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("服务器接收到信息如下");
        String s = new String(msg.getContent(), Charset.forName("utf-8"));
        System.out.println("长度="+msg.getLen());
        System.out.println("内容="+s);
        System.out.println("服务器接收到消息包数量="+(++count));

        //回复消息
        String resp= UUID.randomUUID().toString();
        byte[] bytes=resp.getBytes(Charset.forName("utf-8"));
        int len=bytes.length;
        //构建一个协议包对象
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(len);
        messageProtocol.setContent(bytes);
        ctx.writeAndFlush(messageProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
