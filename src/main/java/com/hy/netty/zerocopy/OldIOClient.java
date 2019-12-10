package com.hy.netty.zerocopy;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

/**
 * 传统客户端
 *
 * @Author yang.hu
 * @Date 2019/11/10 15:29
 */
public class OldIOClient {

    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost", 8899);

        DataOutputStream dataOutputStream= new DataOutputStream(socket.getOutputStream());

        // 这里准备一个文件，尽量大一点
        String fileName = "E:\\开发相关\\视频\\ElasticSearch.zip";
        File file = new File(fileName);
        FileInputStream fileInputStream = new FileInputStream(file);

        System.out.println("开始向服务器发送数据，总字节为：" + file.length());

        // 同服务器保持一致大小
        byte[] byteArray = new byte[409600];
        int read = 0;
        long total = 0;//读取的总字节数

        long start = System.currentTimeMillis();

        while ((read = fileInputStream.read(byteArray)) >= 0) {
            total += read;
            dataOutputStream.write(byteArray);
        }

        System.out.println("发送总字节数：" + total + "，耗时：" + (System.currentTimeMillis() - start));
        // byte[] byteArray = new byte[2048]; 发送总字节数：349818196，耗时：67043
        // byte[] byteArray = new byte[4096]; 发送总字节数：349818196，耗时：28479
        // byte[] byteArray = new byte[40960]; 发送总字节数：349818196，耗时：743

        dataOutputStream.close();
        fileInputStream.close();

    }


}
