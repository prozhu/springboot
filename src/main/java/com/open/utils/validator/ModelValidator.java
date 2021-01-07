package com.open.utils.validator;

import com.alibaba.fastjson.JSON;
import com.open.exceptions.ParamValidatorException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 使用hibernate validator校验框架，在代码中校验实体模型
 * @author zc
 * @createTime 2021/1/7 13:43
 */
@Slf4j
public class ModelValidator {

    /**
     * 校验实体模型的字段是否符合规范
     * (如果不符合规范，就抛出对应的异常，并打印日志)
     * @author ：zc
     * @createTime ：2020年5月25日 下午3:54:09
     * @updateTime ：2020年5月25日 下午3:54:09
     * @param object 待校验的实体模型
     */
    public static void validatorModel(Object object) {
        // 调用模型校验的方法
        Map<String, String> result = validatorModelParam(object);
        if (result.isEmpty()) {
            return;
        }
        String validatorMsg = JSON.toJSONString(result);
        log.error("模型参数校验失败: {}", validatorMsg);
        throw new ParamValidatorException(validatorMsg);
    }

    /***
     * 拼接参数校验的提示语
     * @author zc
     * @createTime 2021/1/6 9:14
     * @param validatorResult
     * @return java.lang.String
     */
    private static String joinValidatorMsg(Map<String, String> validatorResult) {
        if (validatorResult.isEmpty()) {
            return "";
        }
        StringBuilder resultStr = new StringBuilder();
        validatorResult.values().stream().forEach(value -> {
                    resultStr.append(", ").append(value);
                }
        );
        return resultStr.toString().substring(1);
    }

    /**
     * 校验实体模型的字段是否符合规范(可以排除某些字段不参与校验)
     * @author ：zc
     * @createTime ：2020年5月25日 下午3:51:45
     * @updateTime ：2020年5月25日 下午3:51:45
     * @param object 待校验的实体模型
     * @param excludeValidatorFields 不参与校验的字段(可变参数)
     */
    public static void validatorModel(Object object, String... excludeValidatorFields) {
        // 调用模型校验的方法
        Map<String, String> result = validatorModelParam(object, excludeValidatorFields);
        if (result.isEmpty()) {
            return;
        }
        String validatorMsg = JSON.toJSONString(result);
        log.error("模型参数校验失败: {}", validatorMsg);
        throw new ParamValidatorException(validatorMsg);
    }

    /**
     * 校验实体模型的字段是否符合规范
     * @author ：zc
     * @createTime ：2020年1月7日 上午10:04:28
     * @updateTime ：2020年1月7日 上午10:04:28
     * @param object 待校验模型
     * @return：将错误信息，填充到Map集合中返回, 格式：(属性名称, 错误信息)
     */
    public static Map<String, String> validatorModelParam(Object object) {
        Map<String, String> result = new HashMap<String, String>(16);
        // 1. 创建模型校验器
        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class).configure()
                .buildValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> validate = validator.validate(object);
        Iterator<ConstraintViolation<Object>> iterator = validate.iterator();
        while (iterator.hasNext()) {
            ConstraintViolation<Object> next = iterator.next();
            String propertyName = next.getPropertyPath().toString();
            String msg = next.getMessage();
            result.put(propertyName, msg);
        }
        return result;
    }

    /**
     * 校验实体模型的字段是否符合规范（可以排除该模型里面的部分字段，不参与校验）
     * @author ：zc
     * @createTime ：2020年1月7日 下午2:44:26
     * @updateTime ：2020年1月7日 下午2:44:26
     * @alterMan：zc
     * @param object 待校验模型
     * @param fields 模型里面，不参与校验的字段(可变参数)
     * @return：将错误信息，填充到Map集合中返回, 格式：(属性名称, 错误信息)
     */
    public static Map<String, String> validatorModelParam(Object object, String... fields) {
        //获取校验结果
        Map<String, String> validatorResult = validatorModelParam(object);
        if (ObjectUtils.isEmpty(fields)) {
            return validatorResult;
        }
        //排除部分字段的校验
        Iterator<String> iterator = validatorResult.keySet().iterator();
        while (iterator.hasNext()) {
            String propertyName = iterator.next();
            for (int i = 0; i < fields.length; i++) {
                if (propertyName.equals(fields[i])) {
                    validatorResult.remove(propertyName);
                    break;
                }
            }
        }
        return validatorResult;
    }

}
