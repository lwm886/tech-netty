package com.tech.netty.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lw
 * @since 2021/9/8
 */
public class StarProxy {

    private Star star=new Huge();

    public Star getProxy(){
        Object o = Proxy.newProxyInstance(getClass().getClassLoader(), star.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("in invoke method="+method);
                return "hello word";
            }
        });
        return (Star)o;
    }

    public static void main(String[] args) {
        Star star = new StarProxy().getProxy();
        star.sing("神话");
        star.act("琅琊榜");
    }
}
