package com.zc.springboot.controller.demo3;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionTest {
	private void age(int age) {
		log.info("int age = {}", age);
	}

	private void age(Integer age) {
		log.info("Integer age = {}", age);
	}

	@Test
	public void testOne() throws Exception {
		getClass().getDeclaredMethod("age", Integer.TYPE).invoke(this, Integer.valueOf("36"));
		getClass().getDeclaredMethod("age", Integer.class).invoke(this, 36);
	}
}
