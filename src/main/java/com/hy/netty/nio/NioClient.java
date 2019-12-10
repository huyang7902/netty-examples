package com.hy.netty.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 键盘输入
 *
 * @Author yang.hu
 * @Date 2019/11/6 18:57
 */
public class NioClient {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 8899));

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);//连接事件


            while (true) {
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                selectionKeys.forEach(selectionKey -> {
                    try {
                        if (selectionKey.isConnectable()) {
                            SocketChannel client = (SocketChannel) selectionKey.channel();
                            // 判断此通道上是否正在进行连接操作
                            if (client.isConnectionPending()) {
                                client.finishConnect();

                                ByteBuffer writeBuffer = ByteBuffer.allocate(2048);
                                writeBuffer.put((LocalDateTime.now() + " 连接成功").getBytes());
                                writeBuffer.flip();
                                client.write(writeBuffer);

                                // 新开启一个线程，用于监听键盘输入，因为键盘输入是阻塞的
                                ExecutorService executorService = Executors.newSingleThreadExecutor();
                                executorService.submit(() -> {
                                    while (true) {
                                        try {
                                            writeBuffer.clear();
                                            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                            String sendMsg = bufferedReader.readLine();
                                            writeBuffer.put(sendMsg.getBytes());
                                            writeBuffer.flip();
                                            client.write(writeBuffer);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                            // 注册读事件
                            client.register(selector, SelectionKey.OP_READ);
                        } else if (selectionKey.isReadable()) {
                            SocketChannel client = (SocketChannel) selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(2048);
                            int read = client.read(readBuffer);
                            if (read > 0) {
                                readBuffer.flip();
                                String receiveMsg = new String(readBuffer.array());
                                System.out.println(receiveMsg);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                selectionKeys.clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
