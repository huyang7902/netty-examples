package com.hy.netty.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * 字符编码
 *
 * @Author yang.hu
 * @Date 2019/11/9 14:48
 */
public class NioTest13 {

    public static void main(String[] args) throws Exception {
        String inputFile = "nio/NioTest13_in.txt";
        String outFile = "nio/NioTest13_out.txt";

        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile, "r");//只读模式
        RandomAccessFile outRandomAccessFile = new RandomAccessFile(outFile, "rw");//读写模式

        FileChannel inputFileChannel= inputRandomAccessFile.getChannel(); // 获取读通道
        FileChannel outFileChannel= outRandomAccessFile.getChannel(); // 获取写通道

        // 创建内存映射文件
        MappedByteBuffer mappedByteBuffer = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputRandomAccessFile.length());

        System.out.println(mappedByteBuffer.get(12));

        Charset charset = Charset.forName("UTF-8");
        //Charset charset = Charset.forName("iso-8859-1");

        // 创建解码器
        CharsetDecoder charsetDecoder = charset.newDecoder();
        // 创建编码器
        CharsetEncoder charsetEncoder = charset.newEncoder();

        // 解码 字节->字符
        CharBuffer charBuffer = charsetDecoder.decode(mappedByteBuffer);
        // 编码 字符->字节
        ByteBuffer byteBuffer = charsetEncoder.encode(charBuffer);

        outFileChannel.write(byteBuffer);

        inputFileChannel.close();
        outFileChannel.close();

    }

}
