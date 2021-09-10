package com.tech.netty.netty.dubborpc.provider;

import com.tech.netty.netty.dubborpc.netty.NettyServer;

/**
 * @author lw
 * @since 2021/9/8
 */
//启动一个服务提供者 NettyServer
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1",7000);
    }
}
