package com.open.fund;

import lombok.Data;

import java.math.BigDecimal;

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
     * 基金名称
     */
    private String name;

    /**
     * 投资金额
     */
    private String investMoney;

    /**
     * 基金涨跌率
     */
    private BigDecimal gszzl;

    /**
     * 赚的钱
     */
    private BigDecimal money;

    public BigDecimal getMoney() {
        return new BigDecimal(this.investMoney).multiply((this.gszzl.divide(new BigDecimal("100"))));
    }
}
