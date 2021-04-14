package com.open.utils.validator.constraints;

import com.alibaba.fastjson.JSON;
import com.open.utils.validator.ModelValidator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.validation.constraints.Pattern;
import java.util.Map;

/**
 *
 * @author ：zc
 * @createTime ：2021/4/12 18:41
 */
@Slf4j
public class TestPatternFilterSpecChar {

    /**
     *校验字符串的长度（需要剔除 换行符和制表符之后，在判断长度）
     */
    @Test
    public void testPatternFilterSpecChar() {
        TestPatternFilterSpecCharFunction model = new TestPatternFilterSpecCharFunction();
        model.setConSignee(" \t\tall,\nhepython");
        Map<String, String> map = ModelValidator.validatorModelParam(model);
        log.info("result is :{}", JSON.toJSONString(map));
    }
}


@Data
class TestPatternFilterSpecCharFunction {
    /**
     * 该注解的功能为：
     * 1. 先把字符串中，制表符、换行符给去除后，在校验字符串的长度
     */
    @PatternFilterSpecChar(message = "字符长度最多20", value = "^(.{0,20})?$", filterRegex = "\t|\n")
    private String conSignee;
}


