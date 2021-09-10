package com.tech.netty.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lw
 * @since 2021/7/26
 **/
@Slf4j
public class BioServer {



    public static void main(String[] args) throws IOException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        while(true){
            log.info("{}：等待客户端程序连接...",getThread());
            Socket accept = serverSocket.accept();
            log.info("{}：建立连接 {}",getThread(),accept.getInetAddress());
            executorService.execute(()-> handler(accept));
        }
    }

    private static void handler(Socket accept) {
        InputStream inputStream=null;
        try{
            inputStream = accept.getInputStream();
            byte[] bytes = new byte[666666];
            while(true){
                log.info("{}：等待读取数据...",getThread());
                int read = inputStream.read(bytes);
                if(read!=-1){
                    log.info("{}：读取到：{},len={}",getThread(),new String(bytes,0,read, StandardCharsets.ISO_8859_1),read);
                }else{
                    break;
                }
            }
        }catch (Exception e){
            log.error("{}出错了，即将关闭连接",getThread());
        }finally {
            try {
                if(inputStream!=null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                accept.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getThread(){
        return Thread.currentThread().getName()+"-"+Thread.currentThread().getId();
    }
}
