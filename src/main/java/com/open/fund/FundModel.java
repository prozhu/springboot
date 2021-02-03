package com.open.fund;

import lombok.Data;

/**
 * 基金模型
 * @author ：zc
 * @createTime ：2021/2/2 17:01
 */
@Data
public class FundModel {
    
    public FundModel(String code, String investMoney) {
        this.code = code;
        this.investMoney = investMoney;
    }

    public FundModel() {
    }

    /**
     * code码
     */
    private String code;

    /**
     * 投资金额
     */
    private String investMoney;
}
