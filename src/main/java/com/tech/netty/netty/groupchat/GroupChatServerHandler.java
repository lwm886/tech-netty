package com.tech.netty.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lw
 * @since 2021/8/19
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个通道组，管理所有通道（通道需要手动加入，在特定事件下自动移除）
    //GlobalEventExecutor.INSTANCE 单例的全局事件执行器
    private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //新的客户端连接事件 有新的客户端连接第一个触发
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将客户端加入的信息推送给其他客户端
        //channelGroup 会给通道组中所有通道发送消息
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+" 加入聊天 "+sdf.format(new Date())+"\n");
        channelGroup.add(channel);
    }

    //断开连接 将某某客户端下线的消息发送给其他客户端
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+" 下线了 "+sdf.format(new Date())+"\n");
        System.out.println("channelGroupSize="+channelGroup.size());
    }

    //通道处于活动状态，提示客户端上线了
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+" 上线了 "+sdf.format(new Date()));
    }

    //通道处于非活动状态，提示客户端下线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"下线了 "+sdf.format(new Date()));
    }

    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        //获取当前通道
        Channel channel = ctx.channel();
        //遍历channelGroup 根据不同的情况，回送不同的信息
        channelGroup.forEach(c->{
            if(channel!=c){ //不是当前通道转发消息
                c.writeAndFlush("[客户端]"+channel.remoteAddress()+" 发送了消息 "+s+" "+sdf.format(new Date())+"\n");
            }else{ //回显自己发送的消息给自己
                channel.writeAndFlush("[自己]发送了消息"+s+" "+sdf.format(new Date())+"\n");
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       //关闭通道
        ctx.close();
    }
}
