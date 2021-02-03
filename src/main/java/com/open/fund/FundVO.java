package com.open.fund;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 基金响应参数模型
 * @author ：zc
 * @createTime ：2021/2/3 13:14
 */
@Data
public class FundVO {
    /**
     * 基金涨跌率
     */
    private BigDecimal gszzl;

    /**
     * 基金code
     */
    private String code;
    /**
     * 基金名称
     */
    private String name;
}
