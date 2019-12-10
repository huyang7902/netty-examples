package com.hy.netty.test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Author yang.hu
 * @Date 2019/12/2 15:16
 */
public class AtomicUpdaterTest {

    public static void main(String[] args) {

        Person person = new Person();

        //for (int i = 0; i < 10; i++) {
        //    new Thread(()->{
        //
        //        try {
        //            Thread.sleep(10);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //
        //        System.out.println(person.age++);
        //    }).start();
        //}

        AtomicIntegerFieldUpdater<Person> updater = AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(updater.getAndIncrement(person));

            }).start();
        }
    }

}

class Person{
    volatile int age;
}
