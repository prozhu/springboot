package com.open.fund;

import lombok.Data;

/**
 * 基金模型
 * @author ：zc
 * @createTime ：2021/2/2 17:01
 */
@Data
public class FundModel {
    /**
     * code码
     */
    private String code;

    /**
     * 投资金额
     */
    private String investMoney;
}
