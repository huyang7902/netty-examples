package com.hy.netty.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author yang.hu
 * @Date 2019/10/28 15:01
 */
public class MyServer {

    public static void main(String[] args) {
        // 接受请求线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理请求线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 启动器
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) //指定日志级别，针对bossGroup
                    .childHandler(new MyServerInitializer()); //针对childGroup

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}

