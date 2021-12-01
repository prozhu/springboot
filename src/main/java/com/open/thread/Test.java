package com.open.thread;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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


    @org.junit.Test
    public void testTraversal() {
        HashMap<Integer, String> map = new HashMap(16);
        map.put(7, "");
        map.put(11, "");
        map.put(43, "");
        map.put(59, "");
        map.put(19, "");
        map.put(3, "");
        map.put(35, "");

        System.out.println("遍历结果：");
        for (Integer key : map.keySet()) {
            System.out.print(key + " -> ");
        }
    }


    /**
     * 根据身份证判断性别
     * @param idCard 身份证
     * @return  0-男性，1-女性
     */
    private Integer judgeSexByIdCard(String idCard) {
        //身份证号的第17位数字，奇数为男性，偶数为女性
        String value = idCard.substring(16).substring(0, 1);
        if (Objects.equals(value, "X")) {
            return 1;
        }
        if ((Integer.valueOf(value) & 1) == 1) {
            return 0;
        } else {
            return 1;
        }
    }


    @org.junit.Test
    public void testGC() throws Exception{
        Integer value = judgeSexByIdCard("420117199505100835");
        log.info(value + "");
    }

    @org.junit.Test
    public void transformDate() {
        String time = "20210510";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = simpleDateFormat.parse(time);
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            log.info(simpleDateFormat.format(date));
        } catch (ParseException ex) {

        }
    }

    /**
     * 集合分页
     */
    @org.junit.Test
    public void testPartition() {
        List<String> ids = Arrays.asList("1", "2", "2", "2", "2", "2", "2");
        List<List<String>> partition = Lists.partition(ids, 2);
        partition.forEach(e -> {
            e.forEach(t -> {
                log.info(t);
            });
            log.info("...........");
        });

    }

}

