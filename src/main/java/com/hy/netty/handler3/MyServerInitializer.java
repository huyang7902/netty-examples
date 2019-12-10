package com.hy.netty.handler3;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 初始化器
 *
 * @Author yang.hu
 * @Date 2019/10/22 14:39
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        // 获取管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 添加处理器
        pipeline.addLast(new PersonDecoder());
        pipeline.addLast(new PersonEncoder());
        pipeline.addLast( new MyServerHandler());
    }
}
