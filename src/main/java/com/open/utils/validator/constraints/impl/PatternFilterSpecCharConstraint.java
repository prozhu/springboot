package com.open.utils.validator.constraints.impl;

import com.open.utils.validator.constraints.PatternExcludeNextline;
import com.open.utils.validator.constraints.PatternFilterSpecChar;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @PatternFilterSpecChar 的实现类
 * @author zc
 * @createTime 2021/4/12 18:40
 */
public class PatternFilterSpecCharConstraint implements ConstraintValidator<PatternFilterSpecChar, String> {

    private String firstRegex;
    private String regex;

    @Override
    public void initialize(PatternFilterSpecChar constraintAnnotation) {
        firstRegex = constraintAnnotation.filterRegex();
        regex = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        //首次正则表达式处理
        if (!StringUtils.isEmpty(firstRegex)) {
            value = value.replaceAll(firstRegex, "");
        }
        //第二次正则表达式处理
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(value);
        return matcher.matches();
    }
}
