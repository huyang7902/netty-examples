package com.hy.netty.handler3;

/**
 * 消息传输协议
 *
 * @Author yang.hu
 * @Date 2019/12/4 16:06
 */
public class PersonProtocol {

    /**
     * 消息体长度
     */
    private int length;

    /**
     * 消息内容
     */
    private byte[] content;


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
