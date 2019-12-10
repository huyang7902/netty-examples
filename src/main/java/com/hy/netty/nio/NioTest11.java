package com.hy.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 关于Buffer的Scattering(分散)和Gathering(收集)
 *<br/>
 * 将客户端的数据用一个数据收集，然后写回客户端
 *<p/>
 * 测试：注意回车算一个字节
 * <p/>
 * windows: telnet
 * <br/>
 *      telnet localhost 8899
 *<p/>
 * mac：nc
 * <br/>
 *      nc localhost 8899
 *
 *
 * @Author yang.hu
 * @Date 2019/11/2 14:19
 */
public class NioTest11 {

    public static void main(String[] args) throws Exception {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 将serverSocketChannel绑定到8899端口，并接受到此通道套接字的连接
        SocketChannel socketChannel = serverSocketChannel.bind(new InetSocketAddress(8899)).accept();

        //消息总长度
        int messageLength = 2 + 3 + 4;

        // 创建一个长度为3的ByteBuffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[3];

        // 初始化数组
        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(4);

        // 循环读取
        while (true) {
            long bytesRead = 0;

            // 读取消息的长度小于消息总长度时，一直读
            while (bytesRead < messageLength) {
                long read = socketChannel.read(byteBuffers);

                bytesRead += read;

                System.out.println("bytesRead：" + bytesRead);

                Arrays.asList(byteBuffers).stream()
                        .map(byteBuffer -> "position: " + byteBuffer.position() + ", limit: " + byteBuffer.limit())
                        .forEach(System.out::println);

            }

            // 翻转读写模式
            Arrays.asList(byteBuffers)
                    .forEach(byteBuffer -> byteBuffer.flip());

            // 将数据写回客户端
            long bytesWritten = 0;
            while (bytesWritten < messageLength) {
                long write = socketChannel.write(byteBuffers);
                bytesWritten += write;
            }

            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());

            System.out.println("bytesRead: " + bytesRead + ", bytesWritten: " + bytesWritten + ", messageLength: " + messageLength);

        }


    }

}
