package com.hy.netty.handler3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 解码器
 *
 * @Author yang.hu
 * @Date 2019/12/4 16:08
 */
public class PersonDecoder extends ReplayingDecoder<Void> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("PersonDecoder#decode invoke");

        int length = in.readInt();

        byte[] content = new byte[length];

        in.readBytes(content);

        PersonProtocol personProtocol = new PersonProtocol();

        personProtocol.setLength(length);
        personProtocol.setContent(content);

        out.add(personProtocol);
    }
}
