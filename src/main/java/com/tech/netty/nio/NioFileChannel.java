package com.tech.netty.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lw
 * @since 2021/7/29
 **/
public class NioFileChannel {
    public static void main(String[] args) throws IOException {
        String str="hello,中国人";
        FileOutputStream fileOutputStream = new FileOutputStream("d://testfile.txt");
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        //重置position
        byteBuffer.flip();
        channel.write(byteBuffer);
        fileOutputStream.close();
    }
}
