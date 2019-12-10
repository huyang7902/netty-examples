package com.hy.netty.fourth;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 初始化器，每个请求都会创建新的实例
 *
 * @Author yang.hu
 * @Date 2019/10/25 10:12
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new IdleStateHandler(5, 7, 10));//心跳监测处理器

        pipeline.addLast(new MyIdleServerHandler());
    }
}
