package com.hy.netty.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @Author yang.hu
 * @Date 2019/10/28 17:59
 */
public class NioTest1 {

    public static void main(String[] args) throws Exception {
        IntBuffer buffer = IntBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(new SecureRandom().nextInt(20));

        }
        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

    }
}
