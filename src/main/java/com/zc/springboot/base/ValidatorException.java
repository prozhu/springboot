package com.zc.springboot.base;

/**
 * 自定义参数校验异常
 * @author ：djzc
 * @createTime ：2020年5月20日 下午4:14:18 
 * @updateTime ：2020年5月20日 下午4:14:18
 */
public class ValidatorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidatorException(String msg) {
		super(msg);
	}

}
