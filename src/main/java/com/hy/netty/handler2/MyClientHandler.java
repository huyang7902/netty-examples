package com.hy.netty.handler2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @Author yang.hu
 * @Date 2019/10/24 9:52
 */
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);

        String s = new String(bytes, "utf-8");


        //收到消息：cb764dba-966d-43b3-898d-fe9d1012d4f6
        //消息数量：1
        System.out.println("收到消息：" +s);
        System.out.println("消息数量：" + ++count);

        ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), Charset.forName("utf-8"));

        //ctx.writeAndFlush(byteBuf);

    }

    /**
     * 通道是活动状态时回调此方法，向服务器发送第一条数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
            ByteBuf byteBuf = Unpooled.copiedBuffer("msg: " + i, Charset.forName("utf-8"));
            ctx.writeAndFlush(byteBuf);
        }

    }
}

