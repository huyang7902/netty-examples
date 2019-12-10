package com.hy.netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author yang.hu
 * @Date 2019/10/28 17:59
 */
public class NioTest2 {

    public static void main(String[] args) throws Exception {

        System.out.println(NioTest2.class.getProtectionDomain().getCodeSource().getLocation().getPath());


        String path = NioTest2.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        File file = new File("src/main/nio/nioTest2.txt");
        System.out.println(file.getAbsolutePath());

        //FileInputStream fileInputStream = new FileInputStream("target/classes/nio/nioTest2.txt");// 文件位于resources/nio/下面
        FileInputStream fileInputStream = new FileInputStream("nio/nioTest2.txt");// 文件位于 当前项目的根目录下，不参与编译

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);

        channel.read(buffer); // 将channel中的数据读到buffer中

        buffer.flip();

        while (buffer.remaining() > 0) {
            System.out.println((char) buffer.get());
        }

    }
}
