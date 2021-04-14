package com.open.utils.validator.constraints;

import com.open.utils.validator.constraints.impl.PatternExcludeNextlineConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验注解
 * （排除换行符之后，在进行校验，也就是把“换行符替换为空格")
 * ps： 使用示例请参考：
 * @see TestPatternExincludeNextline
 * @author zc
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PatternExcludeNextlineConstraint.class})
public @interface PatternExcludeNextline {

    /**
     * 校验失败的信息
     * @return
     */
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 正则表达式
     * @return
     */
    String value();
}
