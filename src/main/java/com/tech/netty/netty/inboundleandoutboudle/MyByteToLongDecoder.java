package com.tech.netty.netty.inboundleandoutboudle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author lw
 * @since 2021/8/27
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     * 解码器
     * decode方法会根据接收的数据被多次调用，直到list中没有新的元素加入或者入站缓冲区没有可读的字节为止
     * （例子中当可读取字节不足8时候decode会被重复调用几次才停止，只要list没有元素就不会调用后面的入站handler）
     * 只要list不为空就会将list中元素逐一传递给下一个入站处理器，有一个元素下一个处理器就会被调用一次
     * @param ctx 上下文对象
     * @param in 入站的ByteBuf
     * @param out List集合 将解码后的数据传递给下一个入站Handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //因为long有8个字节 需要判断够8个字节可读才进行数据读取
        System.out.println("MyByteToLongDecoder 被调用 可读取字节数="+in.readableBytes());
        if(in.readableBytes()>=8){
            out.add(in.readLong());
        }
    }
}
