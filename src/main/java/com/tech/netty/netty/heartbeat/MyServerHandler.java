package com.tech.netty.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author lw
 * @since 2021/8/20
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        String eventType=null;
        if(evt instanceof IdleStateEvent){
            //将evt转型为IdleStateEvent
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()){
                case READER_IDLE:
                    eventType="读空闲";
                    break;
                case WRITER_IDLE:
                    eventType="写空闲";
                    break;
                case ALL_IDLE:
                    eventType="读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+"--超时时间--"+eventType);
            System.out.println("服务器进行相应处理...");
            //如果发生空闲我们关闭通道
//            ctx.channel().close();
        }


    }
}
