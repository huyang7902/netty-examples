package com.hy.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 客户端聊天初始化器
 *
 * @Author yang.hu
 * @Date 2019/10/24 9:48
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        // 获取管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 添加处理器
        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyLongToByteEncoder());
        pipeline.addLast("myClientHandler", new MyClientHandler());

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("client handlerAdded");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("client channelActive");
    }
}
