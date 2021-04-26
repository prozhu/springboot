package com.open.utils.validator.constraints;

import com.alibaba.fastjson.JSON;
import com.open.utils.validator.ModelValidator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

/**
 * 测试 @PatternExincludeNextline  注解
 * @author ：zc
 * @createTime ：2021/4/12 10:28
 */
@Slf4j
public class TestPatternExincludeNextline {

    /**
     * 验证： @PatternExincludeNextline 的功能
     * @author zc
     * @createTime 2021/4/12 10:34
     */
    @Test
    public void testPatternExincludeNextline() {
        TestPatternExcludeNextlineFunction model = new TestPatternExcludeNextlineFunction();
        model.setConSignee("  \r\n,hello all,\nhe\n\rpy\\r");
        Map<String, String> map = ModelValidator.validatorModelParam(model);
        log.info("result is :{}", JSON.toJSONString(map));
    }
}


@Data
class TestPatternExcludeNextlineFunction {
    @PatternExcludeNextline(message = "字符长度最多20", value = "^(.{0,20})?$")
    private String conSignee;
}






