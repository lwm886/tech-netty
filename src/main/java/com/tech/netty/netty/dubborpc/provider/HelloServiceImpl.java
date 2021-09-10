package com.tech.netty.netty.dubborpc.provider;

import com.tech.netty.netty.dubborpc.publicinterface.HelloService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lw
 * @since 2021/9/8
 */
@Slf4j
public class HelloServiceImpl implements HelloService {

    private static int count=0;

    //当有消费方调用该方法时，就返回一个结果
    @Override
    public String hello(String msg) {
        log.info("收到客户端消息 ={}",msg);
        //根据msg返回不同的结果
        if(msg != null){
            return "你好客户端，我已收到你的消息 【"+msg+"】 第"+(++count)+"次";
        }else{
            return "你好客户端，我已收到你的消息";
        }
    }
}
