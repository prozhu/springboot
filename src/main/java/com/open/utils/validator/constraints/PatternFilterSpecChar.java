package com.open.utils.validator.constraints;

import com.open.utils.validator.constraints.impl.PatternExcludeNextlineConstraint;
import com.open.utils.validator.constraints.impl.PatternFilterSpecCharConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 该正则表达式注解使用场景：
 * 1. 校验字符串长度时，如果需要先剔除： 空格、换行符、制表符 之类的特殊字符后，在统计长度；
 * 2. 传统的 @Pattern  注解就做不到了，因此才有了现在这个注解
 * ps： 使用示例请参考：
 * @see TestPatternFilterSpecChar
 * @author zc
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PatternFilterSpecCharConstraint.class})
public @interface PatternFilterSpecChar {

    /**
     * 校验失败的信息
     * @return
     */
    String message();

    /**
     * 需要过滤掉的字符（正则表达式）
     * @return
     */
    String filterRegex();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 第二次正则表达式(默认)
     * @return
     */
    String value();

}
