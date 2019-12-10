package com.hy.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单聊天服务器
 *
 * @Author yang.hu
 * @Date 2019/11/6 17:46
 */
public class NioServer {

    private static Map<String, SocketChannel> clientMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();//返回服务端套接字
        //SocketChannel socketChannel = SocketChannel.open();
        //Socket socket = socketChannel.socket();//客户端套接字
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {

            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            selectionKeys.forEach(selectionKey -> {
                final SocketChannel client;

                try {
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        client = server.accept();//返回客户端套接字
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);

                        String key = "[" + UUID.randomUUID().toString() + "]";
                        clientMap.put(key, client);
                        System.out.println(key + " 加入服务器");

                    } else if (selectionKey.isReadable()) {
                        client = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int read = client.read(byteBuffer);
                        if (read > 0) {
                            byteBuffer.flip();

                            String msg = new String(byteBuffer.array());

                            System.out.println(client + ": " + msg);


                            String senderKey = "";
                            // 先找到key
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                if (entry.getValue() == client) {
                                    senderKey = entry.getKey();
                                }
                            }

                            //ByteBuffer writeBuffer = ByteBuffer.allocate(2048);
                            //writeBuffer.put((senderKey + ": " + msg).getBytes());
                            //writeBuffer.flip();
                            // 将数据广播每一个客户端
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                SocketChannel socketChannel = entry.getValue();
                                ByteBuffer writeBuffer = ByteBuffer.allocate(2048);
                                writeBuffer.put((senderKey + ": " + msg).getBytes());
                                writeBuffer.flip();
                                socketChannel.write(writeBuffer);
                            }
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            selectionKeys.clear();

        }
    }

}
