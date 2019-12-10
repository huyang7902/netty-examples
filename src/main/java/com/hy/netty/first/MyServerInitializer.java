package com.hy.netty.first;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器，每个请求都会创建新的实例
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
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("myHttpServerHandler", new MyHttpServerHandler());
    }
}
