package com.hy.netty.handler3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 *
 * @Author yang.hu
 * @Date 2019/12/4 16:16
 */
public class PersonEncoder extends MessageToByteEncoder<PersonProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, PersonProtocol msg, ByteBuf out) throws Exception {
        System.out.println("PersonEncoder#encode invoke");

        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());

    }
}
