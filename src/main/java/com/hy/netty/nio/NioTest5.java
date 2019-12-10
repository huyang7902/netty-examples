package com.hy.netty.nio;

import java.nio.ByteBuffer;

/**
 * @Author yang.hu
 * @Date 2019/10/29 17:23
 */
public class NioTest5 {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.putInt(10);
        buffer.putLong(100000L);
        buffer.putDouble(20000D);
        buffer.putShort((short) 6000);
        buffer.putChar('我');

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getChar());
    }

}
