package com.tech.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author lw
 * @since 2021/7/29
 **/
public class NioFileChannelTransferFrom {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("d://a.png");
        FileOutputStream fileOutputStream = new FileOutputStream("d://b.png");
        FileChannel channelIn = fileInputStream.getChannel();
        FileChannel channelOut = fileOutputStream.getChannel();
        channelOut.transferFrom(channelIn,0,channelIn.size());

        channelIn.close();
        channelOut.close();
        fileInputStream.close();
        fileOutputStream.close();

    }
}
