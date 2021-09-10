package com.tech.netty.netty.source.async;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author lw
 * @since 2021/8/11
 **/
//自定义一个Handler
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    static final EventExecutorGroup group = new DefaultEventExecutorGroup(16);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("channel={} | handlerAdded", ctx.channel().id());
    }

    //读取客户端发送的消息
    // ChannelHandlerContext 是一个上下文对象，可以获取通道和管道信息
    // msg 是客户端发送的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        log.info("服务器读取线程 "+Thread.currentThread().getName());
//        ByteBuf byteBuf = (ByteBuf) msg;
//        log.info("客户端发送消息是："+byteBuf.toString(CharsetUtil.UTF_8));
//
//        ctx.channel().eventLoop().execute(() -> {
//            try {
//                log.info("第一个任务开始执行");
//                Thread.sleep(5*1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端 1",CharsetUtil.UTF_8));
//            log.info("第一个提交任务的线程 "+Thread.currentThread().getName());
//        });
//
//        ctx.channel().eventLoop().execute(() -> {
//            try {
//                log.info("第二个任务开始执行");
//                Thread.sleep(5*1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端 1",CharsetUtil.UTF_8));
//            log.info("第二个提交任务的线程 "+Thread.currentThread().getName());
//        });
//
//        ctx.writeAndFlush(Unpooled.copiedBuffer("go on....",CharsetUtil.UTF_8));
        final Object msgCop = msg;
        final ChannelHandlerContext ctxCop = ctx;
        //在handler中添加业务线程池 执行耗时任务
//        group.submit(new Callable<Object>() {
//            @Override
//            public Object call() throws Exception {
//                ByteBuf buff = (ByteBuf) msgCop;
//                byte[] bytes = new byte[buff.readableBytes()];
//                buff.readBytes(bytes);
//                String s = new String(bytes, CharsetUtil.UTF_8);
//                log.info("读取到消息：{}",s);
//                Thread.sleep(10*1000);
//                ctxCop.writeAndFlush(Unpooled.copiedBuffer("hello client",CharsetUtil.UTF_8));
//                log.info("发送完成");
//                return null;
//            }
//        });
//        group.submit(new Callable<Object>() {
//            @Override
//            public Object call() throws Exception {
//                ByteBuf buff = (ByteBuf) msgCop;
//                byte[] bytes = new byte[buff.readableBytes()];
//                buff.readBytes(bytes);
//                String s = new String(bytes, CharsetUtil.UTF_8);
//                log.info("1读取到消息：{}",s);
//                Thread.sleep(10*1000);
//                ctxCop.writeAndFlush(Unpooled.copiedBuffer("hello client1",CharsetUtil.UTF_8));
//                log.info("1发送完成");
//                return null;
//            }
//        });

        ByteBuf buff = (ByteBuf) msgCop;
        byte[] bytes = new byte[buff.readableBytes()];
        buff.readBytes(bytes);
        String s = new String(bytes, CharsetUtil.UTF_8);
        log.info("读取到消息：{}", s);
        Thread.sleep(10 * 1000);

       ctxCop.writeAndFlush(Unpooled.copiedBuffer("hello client", CharsetUtil.UTF_8));
        log.info("发送完成");

        log.info("go on...");
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    //处理异常 一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
