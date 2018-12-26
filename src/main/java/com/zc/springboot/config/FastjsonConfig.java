package com.zc.springboot.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 将springmvc 内置的json工具JacksonJson，替换为 fastjson
 * @author ：djzc
 * @createTime ：2018年12月26日 下午1:55:03 
 * @updateTime ：2018年12月26日 下午1:55:03
 */
@Configuration
@EnableWebMvc
public class FastjsonConfig implements WebMvcConfigurer {
	/**	
	 * 替换框架json为fastjson
	 */

	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// 1.构建了一个HttpMessageConverter FastJson 消息转换器
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter(); //
		// 2.定义一个配置，设置编码方式，和格式化的形式
		FastJsonConfig fastJsonConfig = new FastJsonConfig();

		// 3.设置成了PrettyFormat格式
		/**
		 * SerializerFeature: 有如下属性
		 * PrettyFormat : 是否将json字符串格式化
		 * WriteMapNullValue： 输出空值字段
		 * WriteNullListAsEmpty： list如果是null , 就返回 “[ ]”
		 * WriteNullNumberAsZero: 数值字段如果为null，输出为0
		 * WriteNullBooleanAsFalse:   Boolean字段如果为null，输出为false，而不是null
		 */
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
				SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty);
		List<MediaType> fastMediaTypes = new ArrayList<>();
		// 处理中文乱码问题
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastConverter.setSupportedMediaTypes(fastMediaTypes); //
		// 5.将fastJsonConfig加到消息转换器中
		fastConverter.setFastJsonConfig(fastJsonConfig);

		// 处理字符串, 避免直接返回字符串的时候被添加了引号
		StringHttpMessageConverter smc = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		converters.add(smc);

		converters.add(fastConverter);
	}

}
