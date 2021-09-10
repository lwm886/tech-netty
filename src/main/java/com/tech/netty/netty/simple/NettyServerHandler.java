package com.tech.netty.netty.simple;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lw
 * @since 2021/8/11
 **/
//自定义一个Handler
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("channel={} | handlerAdded",ctx.channel().id());
    }

    //读取客户端发送的消息
    // ChannelHandlerContext 是一个上下文对象，可以获取通道和管道信息
    // msg 是客户端发送的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("服务器读取线程 "+Thread.currentThread().getName());
//        System.out.println("server ctx:"+ctx);
//        Channel channel = ctx.channel();
//        ChannelPipeline pipeline = ctx.pipeline();
//        ByteBuf byteBuf = (ByteBuf) msg;
//        System.out.println("客户端发送消息是："+byteBuf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址："+channel.remoteAddress());
//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5*1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端 1",CharsetUtil.UTF_8));
//                log.info("channel:"+ctx.channel().hashCode());
//            }
//        });
//
//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5*1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端 2",CharsetUtil.UTF_8));
//                log.info("channel:"+ctx.channel().hashCode());
//            }
//        });
//
//        ctx.channel().eventLoop().schedule(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5*1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端 3",CharsetUtil.UTF_8));
//                log.info("channel:"+ctx.channel().hashCode());
//            }
//        },15, TimeUnit.SECONDS);
        Set<SocketChannel> channels = NettyServer.customerChannelList;
        channels.forEach(channel -> {
            channel.eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    channel.writeAndFlush(Unpooled.copiedBuffer("hellow word",CharsetUtil.UTF_8));
                    log.info("eventloop hashcode::{},channel hashcode::"+channel.hashCode(),channel.eventLoop().hashCode());
                }
            });
        });

        log.info("go on...");
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //发送数据
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端", CharsetUtil.UTF_8));
    }

    //处理异常 一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
