package com.zc.springboot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * redis 封装的工具类
 * @author ：djzc
 * @createTime ：2018年12月29日 下午2:04:10 
 * @updateTime ：2018年12月29日 下午2:04:10
 */
//@Service("redis")
public class RedisUtil {
	@Resource
	private JedisPool jedisPool;

	/**
	 * 读取配置文件中的redis默认库号
	 */
	@Value("${spring.redis.database}")
	private int database;

	/**
	 * 往redis中指定的库里，设置值
	 * @author ：djzc
	 * @createTime ：2019年1月2日 下午6:55:25 
	 * @updateTime ：2019年1月2日 下午6:55:25 
	 * @alterMan：djzc
	 * @param database: redis的库号
	 * @param key ： key值
	 * @param value：对应的value
	 * @return：
	 */
	public String setCustomDatabase(int database, String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(database);
			return jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		} finally {
			jedis.close();
		}
	}

	/**
	 * 往redis中默认的库里，设置值
	 * @author ：djzc
	 * @createTime ：2019年1月2日 下午6:55:25 
	 * @updateTime ：2019年1月2日 下午6:55:25 
	 * @alterMan：djzc
	 * @param key ： key值
	 * @param value：对应的value
	 * @return：
	 */
	@SuppressWarnings("unused")
	public String set(String key, String value) {
		return setCustomDatabase(database, key, value);
	}

	/**
	 * 往redis中默认的库里，设置值(带有过期时间)
	 * @author ：djzc
	 * @createTime ：2019年1月2日 下午7:03:08 
	 * @updateTime ：2019年1月2日 下午7:03:08 
	 * @alterMan：djzc
	 * @param key： key值
	 * @param value： 对应的value
	 * @param seconds：时间（秒）
	 * @return：
	 */
	public String setEx(String key, String value, int seconds) {
		return setExCustomDatabase(key, value, seconds, database);
	}

	/**
	 * 往redis中指定的库里，设置值(带有过期时间)
	 * @author ：djzc
	 * @createTime ：2019年1月2日 下午7:03:08 
	 * @updateTime ：2019年1月2日 下午7:03:08 
	 * @alterMan：djzc
	 * @param key： key值
	 * @param value： 对应的value
	 * @param seconds：时间（秒）
	 * @param database：reids的库号
	 * @return：
	 */
	public String setExCustomDatabase(String key, String value, int seconds, int database) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(database);
			return jedis.setex(key, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		} finally {
			jedis.close();
		}
	}

}
