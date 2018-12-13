package com.zc.springboot.controller.demo2;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义配置文件
 * @author ：djzc
 * @createTime ：2018年12月12日 下午2:53:03 
 * @updateTime ：2018年12月12日 下午2:53:03
 */
@Configuration
public class WebConfiguration {

	@Bean
	public RemoteIpFilter remoteIpFilter() {
		return new RemoteIpFilter();
	}

	@Bean
	public FilterRegistrationBean testFilterRegistration() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new MyFilter());
		registration.addUrlPatterns("/*");
		registration.addInitParameter("paramName", "paramValue");
		registration.setName("MyFilter");
		registration.setOrder(1);
		return registration;
	}

	/**
	 * 将自定义Filter加入过滤链
	 * @author ：djzc
	 * @createTime ：2018年12月12日 下午2:55:48 
	 * @updateTime ：2018年12月12日 下午2:55:48
	 */
	public class MyFilter implements Filter {
		@Override
		public void destroy() {
		}

		@Override
		public void doFilter(ServletRequest srequest, ServletResponse sresponse,
				FilterChain filterChain) throws IOException, ServletException {
			HttpServletRequest request = (HttpServletRequest) srequest;
			System.out.println("this is MyFilter,url :" + request.getRequestURI());
			filterChain.doFilter(srequest, sresponse);
		}

		@Override
		public void init(FilterConfig arg0) throws ServletException {
		}
	}
}
