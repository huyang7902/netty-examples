package com.hy.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author yang.hu
 * @Date 2019/10/29 15:54
 */
public class NioTest4 {

    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("nio/input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("nio/output.txt");

        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outChannel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);


        while (true) {
            //buffer.clear(); // 置位初始状态

            int read;

            read = inputChannel.read(buffer);

            System.out.println("read : " +read);

            if (read == -1) {
                break;
            }

            buffer.flip();

            outChannel.write(buffer);

        }

        fileInputStream.close();
        fileOutputStream.close();
    }

}
