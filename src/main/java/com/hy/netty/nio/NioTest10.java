package com.hy.netty.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 文件锁
 *
 * @Author yang.hu
 * @Date 2019/11/2 14:19
 */
public class NioTest10 {

    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("nio/nioTest9.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        // 锁定位置 3->6, true：共享锁；false：排它锁
        FileLock lock = fileChannel.lock(3, 6, true);

        System.out.println("vaild：" + lock.isValid());
        System.out.println("shared：" + lock.isShared());

        // 释放锁
        lock.release();
    }

}
