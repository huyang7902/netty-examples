package com.hy.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * byte -> long 解码器
 *
 * @Author yang.hu
 * @Date 2019/12/4 10:05
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("decode invoke");

        // 判断可读字节数是否足够一个Long(8个字节)
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
