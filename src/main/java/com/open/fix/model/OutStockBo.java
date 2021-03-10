package com.open.fix.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 出库接口的入参模型
 * @author ：zc
 * @createTime ：2021/3/7 15:07
 */
@NoArgsConstructor
@Data
public class OutStockBo {


    /**
     * orderId : DGRB202101161854841519
     * outStockAmount : 1
     * outStockType : 2
     * warehouseId : WH01
     * warehouseAreaId : WA01
     * warehouseLocId : WL001
     * remark : 丰收线下已经配货并发货了。
     */

    private String orderId;
    private int outStockAmount;
    private int outStockType = 2;
    private String warehouseId;
    private String warehouseAreaId;
    private String warehouseLocId;
    private String remark = "系统自动处理：丰收线下已配货并发货";
}
