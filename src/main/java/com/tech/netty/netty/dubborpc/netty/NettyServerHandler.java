package com.tech.netty.netty.dubborpc.netty;

import com.tech.netty.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lw
 * @since 2021/9/8
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息，并调用服务
        log.info("msg= {}",msg.toString());

        //客户端调用服务器的API时，我们需要定义一个协议
        //比如我们要求发送消息必须以某个字符串开头 "HelloService#hello#你好"
        if(msg.toString().startsWith("HelloService#hello#")){
            String hello = new HelloServiceImpl().hello(msg.toString().substring(
                    msg.toString().lastIndexOf("#")+1
            ));
            ctx.writeAndFlush(hello);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
