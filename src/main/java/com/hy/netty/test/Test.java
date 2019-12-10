package com.hy.netty.test;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * @Author yang.hu
 * @Date 2019/11/11 18:17
 */
public class Test {

    public static void main(String[] args) {

        int max = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println(max);

        Integer a = null;
        System.out.println(a==1);
        
    }
}
