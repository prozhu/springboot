package com.zc.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * springboot 启动类，只会扫描，这个文件所在包的子包，因此需要加上指定的扫描注解，让其扫描指定包底下的接口
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.**" })
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
