package com.open.utils.validator.constraints.impl;

import com.open.utils.validator.constraints.PatternExcludeNextline;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @PatternExcludeNextline 的实现类
 * @author ：zc
 * @createTime ：2021/4/8 16:42
 */
public class PatternExcludeNextlineConstraint implements  ConstraintValidator<PatternExcludeNextline, String>{

    private String regex;
    @Override
    public void initialize(PatternExcludeNextline constraintAnnotation) {
        regex = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        value = value.replaceAll("\n", "");
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(value);
        return  matcher.matches();
    }
}
