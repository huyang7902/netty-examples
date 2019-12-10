package com.hy.netty.zerocopy;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * NIO的服务器
 *
 * @Author yang.hu
 * @Date 2019/11/10 16:16
 */
public class NewIOServer {

    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 服务器绑定端口的两种方式
        // 1、直接调用serverSocketChannel的bind方法
        serverSocketChannel.bind(new InetSocketAddress(8899));
        // 2、1.7开始， 获取socket对象，调用bind方法
        //ServerSocket socket = serverSocketChannel.socket();
        //socket.setReuseAddress(true); //是否重用地址，当上一个链接处于TIME_WAIT时
        //socket.bind(new InetSocketAddress(8899));


        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);//如果使用selector时一定要设置为非阻塞，这里没有使用就设置为阻塞

            int read = 0;
            int total = 0;

            long start = System.currentTimeMillis();

            while (-1 != read) {
                read = socketChannel.read(byteBuffer);
                total += read;
                byteBuffer.rewind();

                System.out.println("读取字节数："+ read + "读取总字节数：" + total );
            }

            System.out.println("读取总字节数：" + total + "，耗时：" + (System.currentTimeMillis() - start));


        }
    }

}
