package com.hy.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Long -> Byte 编码器
 *
 * @Author yang.hu
 * @Date 2019/12/4 10:14
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("encode invoke");
        System.out.println(msg);
        out.writeLong(msg);

    }
}
