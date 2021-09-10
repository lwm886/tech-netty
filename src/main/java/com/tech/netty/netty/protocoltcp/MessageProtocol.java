package com.tech.netty.netty.protocoltcp;

/**
 * @author lw
 * @since 2021/8/31
 */
//协议包对象
public class MessageProtocol {
    private int len; //根据这个长度设置读取数据长度
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
