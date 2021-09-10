package com.tech.netty.netty.source.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lw
 * @since 2021/9/3
 */
@Slf4j
public class TestServerHandler1 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("in TestServerHandler1..............");
        ctx.fireChannelRead(msg);
    }
}
