package com.tech.netty.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author lw
 * @since 2021/9/8
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    //上下文
    private ChannelHandlerContext ctx;
    //返回的结果
    private String result;
    //客户端调用方法时 传入的参数
    private String para;

    //通道进入活动状态后被调用 该方法是第一个被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive 被调用");
        this.ctx=ctx;
    }

    //收到服务器的数据后，调用方法
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("channelRead 被调用");
        this.result=msg.toString();
        notify(); //唤醒等待的线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //被代理对象调用 发送数据给服务器 -> wait ->等待被唤醒(channelRead) -> 返回结果
    @Override
    public synchronized Object call() throws Exception {
        log.info("call 被调用");
        ctx.writeAndFlush(para);
        //进行wait
        wait(); //等待channelRead方法获取到服务器的结果后，唤醒
        log.info("call 再次被调用");
        return result; //服务方返回的结果
    }

    void setPara(String param){
        System.out.println("setPara ");
        this.para=param;
    }
}
