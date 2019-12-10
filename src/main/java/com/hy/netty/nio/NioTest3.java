package com.hy.netty.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author yang.hu
 * @Date 2019/10/29 11:36
 */
public class NioTest3 {

    public static void main(String[] args) throws Exception{

        FileOutputStream fileOutputStream = new FileOutputStream("nio/nioTest3.txt");

        FileChannel channel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        byteBuffer.put("hello world, nio!".getBytes());

        byteBuffer.flip(); // 翻转状态

        channel.write(byteBuffer); // 将缓冲区的数据写到channel中

        fileOutputStream.close();


    }

}
