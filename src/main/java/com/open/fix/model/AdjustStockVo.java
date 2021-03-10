package com.open.fix.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 异常订单统计模型
 * @author ：zc
 * @createTime ：2021/3/7 15:49
 */
@Data
public class AdjustStockVo {
    //无库存信息
    private List<String> noStock = new ArrayList<>();
    private int notStockSize;
    //入库数量为0
    private List<String> noStock2 = new ArrayList<>();
    private int notStock2Size;
    //库存数量不为0
    private List<String> noStock3 = new ArrayList<>();
    private int notStock3Size;

    //库存数量为0
    private List<String> noStock4 = new ArrayList<>();
    private int notStock4Size;

    public void setNoStock(List<String> noStock) {
        this.noStock = noStock;
        this.notStockSize = noStock.size();
    }

    public void setNoStock2(List<String> noStock2) {
        this.noStock2 = noStock2;
        this.notStock2Size = noStock2.size();
    }

    public void setNoStock3(List<String> noStock3) {
        this.noStock3 = noStock3;
        this.notStock3Size = noStock3.size();
    }

    public void setNoStock4(List<String> noStock4) {
        this.noStock4 = noStock4;
        this.notStock4Size = noStock4.size();
    }
}
