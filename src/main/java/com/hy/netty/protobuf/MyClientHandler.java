package com.hy.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @Author yang.hu
 * @Date 2019/10/28 15:06
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            int n = 0;
            int m = 3;
            //生成[0, 3)的随机数
            //double random = Math.random() * (n-m) + m;
            int random = new Random().nextInt(3);
            System.out.println(random);

            MyDataInfo.MyMessage myMessage;

            if (0 == random) {
                MyDataInfo.Person person = MyDataInfo.Person.newBuilder().setName("人").setAge(0).setAdress("北京").build();
                myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.PersonType).setPerson(person).build();
            } else if (1 == random) {
                MyDataInfo.Dog dog = MyDataInfo.Dog.newBuilder().setName("狗").setAge(0).build();
                myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.DogType).setDog(dog).build();
            } else {
                MyDataInfo.Cat cat = MyDataInfo.Cat.newBuilder().setName("猫").setCity("成都").build();
                myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.CatType).setCat(cat).build();
            }

            ctx.writeAndFlush(myMessage);
            Thread.sleep(500);
        }
    }
}
