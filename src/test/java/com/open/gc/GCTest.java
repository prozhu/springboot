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


    @Test
    public void generateSQL() {
        String[] sql = {"insert into xdpcp.tc_specialcar(special_carowner_name, plate_no, plate_type, endtime, contact, phone, create_time, type, remark, parkpotid, create_operatorid) values ('清投公务车', '鄂E1NK68', '02', '', '清投公务车', '13888888888', '2022-03-25 16:26:57', '2', '清投公务车', '停车场id', 'admin');",
                "insert into xdpcp.tc_specialcar(special_carowner_name, plate_no, plate_type, endtime, contact, phone, create_time, type, remark, parkpotid, create_operatorid) values ('城投公务车', '鄂EKT302', '02', '', '城投公务车', '13888888888', '2022-03-25 16:26:57', '2', '城投集团公务车', '停车场id', 'admin');",
                "insert into xdpcp.tc_specialcar(special_carowner_name, plate_no, plate_type, endtime, contact, phone, create_time, type, remark, parkpotid, create_operatorid) values ('城投公务车', '鄂EZZ368', '02', '', '城投公务车', '13888888888', '2022-03-25 16:26:57', '2', '城投集团公务车', '停车场id', 'admin');"};

        String[] parkpotIdArr = {"42052800007","42052800006","42052800015","42052800005","42052800016","42052800002","42052800004","42052800018","42052800017","42052800001","42052800008","42052800011","42052800003","42052800010","42052800009","42052800012","42052800013"};
        int index = 0;
        for (String s : sql) {
            for (String parkpotId : parkpotIdArr) {
                index++;
                //替换停车场的编号
                String newSql = s.replace("停车场id", parkpotId);
                //对白名单的截止时间，做特殊处理，不然添加不到数据库里面
                newSql = newSql.replace("'02', ''", "'02', null");
                System.out.println(newSql);
            }
        }
        log.info("总数为：{}", index);
    }























}