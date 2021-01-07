package com.open.utils.validator;

import com.alibaba.fastjson.JSON;
import org.hibernate.validator.constraints.Length;
import org.junit.Test;

import javax.validation.constraints.NotBlank;

/**
 *ModelValidator 工具测试类
 * @author ：zc
 * @createTime ：2021/1/7 17:12
 */
public class ModelValidatorTest {


    /**
     * 测试对象中，传入属性的差集
     * @author zc
     * @createTime 2021/1/7 17:25
     */
    @Test
    public void testGetAnotherPropertyField() {
        String[] erpCustomerIds = ModelValidator.getAnotherPropertyField(new AddAddressBO(), "erpCustomerId");
        System.out.println(JSON.toJSONString(erpCustomerIds));
    }
}










class AddAddressBO {
    /**
     * 外部供应商客户编号
     */
    @NotBlank(message = "外部供应商客户编号不能为空")
    private String erpCustomerId;
    /**
     * 收货人名称
     */
    @NotBlank(message = "收货人名称不能为空")
    @Length(min = 1, max = 30, message = "收货人名称最多30个字符")
    private String conSignee;
    /**
     * 联系方式
     */
    @NotBlank(message = "联系方式不能为空")
    @Length(min = 1, max = 30, message = "联系方式最多30个字符")
    private String contactway;
    /**
     * 地址详情
     */
    private String addressDetail;
    /**
     * 地址备注
     */
    @Length(min = 1, max = 30, message = "详细地址最多30个字符")
    @NotBlank(message = "详细地址不能为空")
    private String addressremark;

    /**
     * 外部erp地址不编号
     */
    @NotBlank(message = "地址编号不能为空")
    private String addressId;

}