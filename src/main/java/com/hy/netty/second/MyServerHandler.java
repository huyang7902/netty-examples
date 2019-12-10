package com.hy.netty.second;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @Author yang.hu
 * @Date 2019/10/22 14:52
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 读取客户端请求，并返回给客户端响应
     * <p>
     * 可以使用 lsof -i : port 查看端口占用情况
     * list open files
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println(msg);

        System.out.println(ctx.channel().remoteAddress());

        ctx.writeAndFlush("from server: " + UUID.randomUUID());
    }

    /**
     * 出现异常，关闭连接
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        ctx.close();
    }
}


