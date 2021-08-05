package com.open.thread.one;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * 多核场景下的“变量”可见性
 * @author Prozhu
 * @date 2021/7/16 14:34
 */
@Slf4j
public class Test {
    private volatile static long count = 0;

    private void add10K() {
        int idx = 0;
        while (idx++ < 10000) {
            count += 1;
        }
    }

    public static long calc() throws InterruptedException {
        Test test = new Test();
        // 创建两个线程，执行 add() 操作
        Thread th1 = new Thread(() -> {
            test.add10K();
        });
        Thread th2 = new Thread(() -> {
            test.add10K();
        });
        // 启动两个线程
        th1.start();
        th2.start();
        // 等待两个线程执行结束
        th1.join();
        th2.join();
        return Test.count;
    }

    public static void main(String[] args) throws InterruptedException {
        log.info(Long.toString(calc()));
    }

}

