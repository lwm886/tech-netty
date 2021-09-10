package com.tech.netty.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * @author lw
 * @since 2021/8/31
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("客户端接收到消息如下");
        System.out.println("长度="+msg.getLen());
        System.out.println("内容="+new String(msg.getContent(),Charset.forName("utf-8")));
        System.out.println("客户端接收到消息数量="+(++count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送5条数据
        for (int i = 0; i < 5; i++) {
            String msg="今天天气冷，吃火锅";
            byte[] bytes = msg.getBytes(Charset.forName("utf-8"));
            int len=bytes.length;
            //创建协议包对象写入缓冲区
            MessageProtocol protocolMessage = new MessageProtocol();
            protocolMessage.setLen(len);
            protocolMessage.setContent(bytes);
            ctx.writeAndFlush(protocolMessage);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
