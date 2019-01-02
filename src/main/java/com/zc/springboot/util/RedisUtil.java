package com.zc.springboot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * redis 封装的工具类
 * @author ：djzc
 * @createTime ：2018年12月29日 下午2:04:10 
 * @updateTime ：2018年12月29日 下午2:04:10
 */
@Service("redis")
public class RedisUtil {
	@Autowired
	private StringRedisTemplate template;

	private void setValue(String key, String value) {
		template.opsForValue().set(key, value);
	}

}
