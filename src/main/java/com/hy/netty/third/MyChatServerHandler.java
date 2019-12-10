package com.hy.netty.third;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Author yang.hu
 * @Date 2019/10/24 14:53
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        System.out.println("【"+ channel.remoteAddress()+"】: " + msg);


        channelGroup.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush("【" + channel.remoteAddress() + "】" + " 发送消息：" + msg + "\n");
            } else {
                ch.writeAndFlush( "【自己】 发送消息：" + msg + "\n");
            }
        });

    }

    /**
     * 当有客户端加入时，会回调此方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        // 向所有连接的客户端广播消息
        channelGroup.writeAndFlush("【服务器】- " + channel.remoteAddress() + "加入\n");

        channelGroup.add(channel);
    }

    /**
     * 当有客户端断开时，会回调此方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        // 向所有连接的客户端广播消息
        channelGroup.writeAndFlush("【服务器】- " + channel.remoteAddress() + "离开\n");
    }

    /**
     * 当有客户端断开时，会回调此方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        System.out.println(channel.remoteAddress() + " 上线");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        System.out.println(channel.remoteAddress() + " 下线");

    }
}
