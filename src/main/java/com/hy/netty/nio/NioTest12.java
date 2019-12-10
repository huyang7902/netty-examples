package com.hy.netty.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * selector， 单线程监听5个端口号
 *
 * @Author yang.hu
 * @Date 2019/11/4 17:25
 */
public class NioTest12 {

    public static void main(String[] args) throws Exception {
        int[] ports = new int[5];

        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        Selector selector = Selector.open();

        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);//设置为非阻塞
            ServerSocket socket = serverSocketChannel.socket();
            socket.bind(new InetSocketAddress(ports[i]));

            // 注册连接
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("监听端口：" + ports[i]);
        }

        while (true) {
            int select = selector.select();//等待事件发生
            System.out.println("select: " + select);

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys: " + selectionKeys);

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isAcceptable()) {//可以接受连接
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    //注册读
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    //将自己删除
                    iterator.remove();

                    System.out.println("获得客户端连接：" + socketChannel);
                } else if (selectionKey.isReadable()) {

                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    int bytesRead = 0;

                    ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                    while (true) {
                        byteBuffer.clear();

                        int read = socketChannel.read(byteBuffer);
                        if (read == -1 || read == 0) { //有可能为0
                            break;
                        }

                        byteBuffer.flip();

                        //将数据写回客户端
                        socketChannel.write(byteBuffer);

                        bytesRead += read;

                    }
                    System.out.println("读取：" + bytesRead + ", 来自于：" + socketChannel);
                    iterator.remove();
                }
            }
        }


    }
}
