package com.hy.netty.zerocopy;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统服务端
 *
 * @Author yang.hu
 * @Date 2019/11/10 15:24
 */
public class OldIOServer {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8899);

        while (true) {
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();//获取输入流

            DataInputStream dataInputStream = new DataInputStream(inputStream);

            byte[] byteArray = new byte[409600];
            long total = 0;
            long start = System.currentTimeMillis();
            // 循环读取
            while (true) {

                int read = dataInputStream.read(byteArray);
                if (-1 == read) {
                    break;
                }

                total += read;

                System.out.println("读取字节数："+ read + "读取总字节数：" + total );

            }

            System.out.println("读取总字节数：" + total + "，耗时：" + (System.currentTimeMillis() - start));

        }


    }

}
