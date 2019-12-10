package com.hy.netty.handler3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 演示拆包和粘包
 *
 * @Author yang.hu
 * @Date 2019/10/22 14:38
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
                    .childHandler(new MyServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
