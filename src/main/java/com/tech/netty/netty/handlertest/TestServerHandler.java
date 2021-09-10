package com.tech.netty.netty.handlertest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter
 * 客户端与服务端相互通信的数据被封装成 HttpObject
 * @author lw
 * @since 2021/8/17
 */
@Slf4j
public class TestServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 新的客户端连接事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerAdded 新的客户端连接事件 | channel={} remote={} ",ctx.channel().id(),ctx.channel().remoteAddress());
    }

    /**
     * 通道注册事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelRegistered 通道注册事件 | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
    }

    /**
     * 通道处于活动状态事件
     * 通道完成注册后触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive 通道处于活动状态事件 | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
    }

    /**
     * 通道数据可读取事件
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断msg是不是HttpRequest请求
        if(msg instanceof HttpRequest){
            log.info("channelRead0 通道数据可读取事件 | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
            ByteBuf content = Unpooled.copiedBuffer("hello 这里是服务器", CharsetUtil.UTF_8);
            //构造http响应
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().add(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
            response.headers().add(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            //将构建好response返回
            ctx.writeAndFlush(response);
        }else{
            log.info("channelRead0 通道数据可读取事件 | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
        }
    }

    /**
     * 通道数据读取完毕事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete 通道数据读取完毕事件 | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
    }

    /**
     * 通道进入非活动状态事件（断开连接）
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive 通道进入非活动状态事件(断开连接) | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
    }

    /**
     * 通道移除事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelUnregistered 通道移除事件 | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
    }

    /**
     * 处理器移除事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerRemoved 处理器移除事件 | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
    }
}
