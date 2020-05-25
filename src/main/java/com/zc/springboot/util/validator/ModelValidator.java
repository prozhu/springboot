package com.zc.springboot.util.validator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.validator.HibernateValidator;

import com.alibaba.fastjson.JSON;
import com.zc.springboot.base.ValidatorException;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用hibernate validator校验框架，在代码中校验实体模型
 * @author ：djzc
 * @createTime ：2019年11月1日 上午10:19:26 
 * @updateTime ：2019年11月1日 上午10:19:26
 */
@Slf4j
public class ModelValidator {

	/**
	 * 校验实体模型的字段是否符合规范
	 * @author ：djzc
	 * @createTime ：2019年11月1日 上午10:23:13 
	 * @updateTime ：2019年11月1日 上午10:23:13 
	 * @alterMan：djzc
	 * @param object：模型类
	 * @return ： 如果不符合校验，就抛出异常，进入到全局异常捕获器处理
	 */
	public static void validatorModel(Object object) {
		// 调用封装的校验方法验证
		Map<String, String> result = validatorModelParam(object);
		if (result.isEmpty()) {
			return;
		}
		String validatorMsg = JSON.toJSONString(result);
		log.error("该模型的校验信息为：{}", validatorMsg);
		throw new ValidatorException(validatorMsg);
	}

	/**
	 * 校验的实体模型是否符合规范（可以排除某些字段不参与校验）
	 * @author ：djzc
	 * @createTime ：2020年5月25日 下午3:28:03 
	 * @updateTime ：2020年5月25日 下午3:28:03 
	 * @alterMan：djzc
	 * @param object 待校验的实体模型
	 * @param excludeValidatorFields 不想校验的字段(可变参数)
	 */
	public static void validatorModel(Object object, String... excludeValidatorFields) {
		// 调用封装的校验方法验证（可以排除某些字段不参与校验）
		Map<String, String> result = validatorModelParam(object, excludeValidatorFields);
		if (result.isEmpty()) {
			return;
		}
		String validatorMsg = JSON.toJSONString(result);
		log.error("该模型的校验信息为：{}", validatorMsg);
		throw new ValidatorException(validatorMsg);
	}

	/**
	 * 校验实体模型的字段是否符合规范
	 * @author ：djzc
	 * @createTime ：2020年1月7日 上午10:04:28 
	 * @updateTime ：2020年1月7日 上午10:04:28 
	 * @alterMan：djzc
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
	 * 校验实体模型的字段是否符合规范（可以排除该模型里面的字段，不参与校验）
	 * @author ：djzc
	 * @createTime ：2020年1月7日 下午2:44:26 
	 * @updateTime ：2020年1月7日 下午2:44:26 
	 * @alterMan：djzc
	 * @param object 待校验模型
	 * @param fields 模型里面，不参与校验的字段(可变参数)
	 * @return：将错误信息，填充到Map集合中返回, 格式：(属性名称, 错误信息)
	 */
	public static Map<String, String> validatorModelParam(Object object, String... fields) {
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
			if (!ArrayUtils.isEmpty(fields)) {
				for (int i = 0; i < fields.length; i++) {
					if (propertyName.equals(fields[i])) {
						result.remove(propertyName);
						break;
					}
				}
			}
		}
		return result;
	}

}
