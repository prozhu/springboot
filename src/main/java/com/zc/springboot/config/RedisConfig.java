package com.zc.springboot.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * springboot2.0 整合redis
 * 配置Spring的CacheManager为redis，即可指定使用redis做缓存
 * @author ：djzc
 * @createTime ：2018年12月29日 下午1:45:30 
 * @updateTime ：2018年12月29日 下午1:45:30
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	private static final Logger LOGGER = LogManager.getLogger(RedisConfig.class.getName());

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.redis.jedis.pool.max-active}")
	private int maxActive;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.jedis.pool.min-idle}")
	private int minIdle;

	@Value("${spring.redis.jedis.pool.max-active}")
	private long maxWaitMillis;

	@Bean
	public JedisPool redisPoolFactory() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		jedisPoolConfig.setMaxTotal(maxActive);
		jedisPoolConfig.setMinIdle(minIdle);
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, null);

		LOGGER.info("JedisPool注入成功！");
		LOGGER.info("redis地址：" + host + ":" + port);
		return jedisPool;
	}

}
