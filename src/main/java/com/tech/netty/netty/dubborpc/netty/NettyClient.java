package com.tech.netty.netty.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lw
 * @since 2021/9/9
 */
@Slf4j
public class NettyClient {
    //创建线程池
    private static ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static NettyClientHandler nettyClientHandler;

    private int count = 0;

    //使用代理模式 获取一个代理对象
    public Object getBean(final Class<?> serviceClass, final String providerName) {
        //返回代理对象，InvocationHandler返回的是调用代理对象方法时候返回的结果
        //如果有多个方法用method区分，不同方法执行不同逻辑 此处测试只有一个方法 所以没有区分method
        //使用线程池提交一个callable 会调用callable的call方法，也就是nettyClientHandler的call方法
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass}, (proxy, method, args) -> {
                    log.info("(proxy, method, args) -> ...进入{}次", ++count);

                    if (nettyClientHandler == null) {
                        initClient();
                    }

                    //设置要发给服务端的消息
                    //providerName 协议头，args[0]是获取调用方法时候的参数
                    nettyClientHandler.setPara(providerName + args[0].toString());
                    return exec.submit(nettyClientHandler).get();
                });
    }

    //初始化客户端
    private void initClient() {
        nettyClientHandler = new NettyClientHandler();

        //创建NioEventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(nettyClientHandler);
                    }
                });
        try {
            bootstrap.connect("127.0.0.1", 7000).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
