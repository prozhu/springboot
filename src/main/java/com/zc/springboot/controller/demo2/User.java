package com.zc.springboot.controller.demo2;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String age;
	private String regTime;
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
