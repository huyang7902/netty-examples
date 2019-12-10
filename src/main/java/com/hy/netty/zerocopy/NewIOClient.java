package com.hy.netty.zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * NIO的客户端
 *
 * @Author yang.hu
 * @Date 2019/11/10 16:39
 */
public class NewIOClient {

    public static void main(String[] args) throws Exception {

        SocketChannel socketChannel = SocketChannel.open();

        //这里设置为阻塞，原因是java.nio.channels.FileChannel.transferTo方法
        //如果目标通道是非阻塞的并且其输出缓冲区中的自由空间少于 count 个字节，则所传输的字节数要小于请求的字节数
        socketChannel.configureBlocking(true);

        // 两种连接服务器方式
        // 1、获取socket对象，调用connect
        //Socket socket = socketChannel.socket();
        //socket.connect(new InetSocketAddress("localhost", 8899));
        // 2、1.7开始，socketChannel的connect方法
        socketChannel.connect(new InetSocketAddress("localhost", 8899));// 这里用必须connect连接服务端，

        // 这里准备一个文件，尽量大一点
        String fileName = "E:\\开发相关\\视频\\ElasticSearch.zip";
        File file = new File(fileName);

        long start = System.currentTimeMillis();
        long total = 0;

        //方式一：fileChannel的transferTo方法
        FileChannel fileChannel = new FileInputStream(file).getChannel();
        //System.out.println(fileChannel.size());//349818196
        //System.out.println(file.length());//349818196
        // 错误方式
        //total = fileChannel.transferTo(0, file.length(), socketChannel);// 完成零拷贝
        // 发送总字节数：8388608，耗时：97,  8M
        // 字节数少于实际数量333.612628936768M，Windows对这种操作有大小限制

        //正确做法，根据transfer的值判断是否读取完
        long position = 0;//指针位置
        while (position <= file.length()) {
            long transfer = fileChannel.transferTo(position, file.length(), socketChannel);
            if (transfer <= 0) {
                break;
            }
            position += transfer;
            total +=transfer;
        }
        // 发送总字节数：349818196，耗时：1014

        // 方式二：使用内存文件映射方式
        //  fileChannel.map说明
        //对于大多数操作系统而言，与通过普通的 read 和 write 方法读取或写入数千字节的数据相比，将文件映射到内存中开销更大。
        // 从性能的观点来看，通常将相对较大的文件映射到内存中才是值得的。
        //RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        //FileChannel fileChannel = randomAccessFile.getChannel();
        //MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, file.length());
        //total = socketChannel.write(mappedByteBuffer);
        //发送总字节数：349818196，耗时：2973

        System.out.println("发送总字节数：" + total + "，耗时：" + (System.currentTimeMillis() - start));

        //fileChannel.close();
        socketChannel.close();


    }
}
