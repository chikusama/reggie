package com.itheima.atomicInteger;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

/**
 * AtomicInteger原子计数器：多线程环境下对值进行原子性操作
 */
public class AtomicIntegerTest {
    //定义一个计数器
    int count1 = 0;
    //原子计数器
    private static AtomicInteger count2 = new AtomicInteger(0);

    @Test
    public void test1() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        count1++;
                    }
                }
            }).start();
        }
        Thread.sleep(1 * 1000);
        System.out.println(count1);
    }

    @Test
    public void test2() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        //将当前值原子地加1
                        count2.getAndIncrement();
                    }
                }
            }).start();
        }
        Thread.sleep(1 * 1000);
        System.out.println(count2);
    }
}
