package com.tech.netty.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter
 * 客户端与服务端相互通信的数据被封装成 HttpObject
 * @author lw
 * @since 2021/8/17
 */
public class TestServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * @description 读取客户端数据
     * @author lw
     * @since 2021/8/17
     **/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        //判断msg是不是HttpRequest请求
        if(msg instanceof HttpRequest){
            System.out.println("pipeline hashCode="+ctx.pipeline().hashCode()+",TestServerHandler hashCode="+this.hashCode());

            System.out.println("msg 类型="+msg.getClass());
            System.out.println("客户端地址="+ctx.channel().remoteAddress());
            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求了favicon.ico，不做响应");
                return;
            }

            //回复信息给浏览器 【http协议】
            ByteBuf content = Unpooled.copiedBuffer("hello,这里是服务器", CharsetUtil.UTF_8);
            //构造http响应
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().add(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
            response.headers().add(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            //将构建好response返回
            ctx.writeAndFlush(response);
        }
    }
}
