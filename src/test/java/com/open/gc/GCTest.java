package com.open.gc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class GCTest {

    public static void main(String[] args) {
        byte[] allocation1, allocation2,allocation3,allocation4,allocation5;
        allocation1 = new byte[32000*1024];
        allocation2 = new byte[1000*1024];
        allocation3 = new byte[1000*1024];
        allocation4 = new byte[1000*1024];
        allocation5 = new byte[1000*1024];
    }

    @Test
    public void getHeapParam() {
        //最大堆内存(-Xmx)和最小堆内存(-Xms)
        //vm 参数：打印出内存分配的详情： -Xmx20m -Xms5m -XX:+PrintGCDetails
        //系统最大空间
//        log.info("Xmx = {}M", Runtime.getRuntime().maxMemory() / 1024.0 / 1024);
//        //系统的空闲空间
//        log.info("free memory = {}M", Runtime.getRuntime().freeMemory() / 1024.0 / 1024);
//        //当前可用的总空间
//        log.info("total memory = {}M", Runtime.getRuntime().totalMemory() / 1024.0 / 1024);

        //-----------
        byte[] b = new byte[10 * 1024 * 1024];
        System.gc();
        log.info("分配了1M空间给数组");
        //系统最大空间
        log.info("Xmx = {}M", Runtime.getRuntime().maxMemory() / 1024.0 / 1024);
        //系统的空闲空间
        log.info("free memory = {}M", Runtime.getRuntime().freeMemory() / 1024.0 / 1024);
        //当前可用的总空间
        log.info("total memory = {}M", Runtime.getRuntime().totalMemory() / 1024.0 / 1024);
    }


    @Test
    public void test22() {
        System.out.println(System.getProperties());

    }





















}