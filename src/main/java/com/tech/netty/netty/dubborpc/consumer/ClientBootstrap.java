package com.tech.netty.netty.dubborpc.consumer;

import com.tech.netty.netty.dubborpc.netty.NettyClient;
import com.tech.netty.netty.dubborpc.publicinterface.HelloService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lw
 * @since 2021/9/9
 */
@Slf4j
public class ClientBootstrap {
    //这里定义协议头
    public static final String protocol="HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {
        //创建一个消费者
        NettyClient nettyClient = new NettyClient();

        //创建代理对象
        HelloService helloService = (HelloService)nettyClient.getBean(HelloService.class, protocol);

        for (;;){
            Thread.sleep(2*1000);
            //通过代理对象调用服务提供者的方法
            String res = helloService.hello("你好 dubbo~");
            log.info("调用的结果 res={}",res);
        }

    }
}
