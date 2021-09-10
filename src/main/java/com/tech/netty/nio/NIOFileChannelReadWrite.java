package com.tech.netty.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lw
 * @since 2021/7/29
 **/
public class NIOFileChannelReadWrite {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel channelIn = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel channelOut = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while(true){
            byteBuffer.clear();
            int read = channelIn.read(byteBuffer);
            System.out.println("read="+read);
            if(read==-1){
                break;
            }
            byteBuffer.flip();
            channelOut.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
