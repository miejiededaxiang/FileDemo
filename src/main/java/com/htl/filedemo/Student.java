package com.htl.filedemo;

import java.io.Serializable;

/**
 * 作者：htl
 * 时间：2017/3/17:17:21
 * 邮箱：
 * 说明：
 */
class Student implements Serializable {


	private String name;//学生姓名
	private transient int age;//年龄设置为瞬时变量，将不被序列化


	public Student(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	@Override
	public String toString() {

		return name+age;
	}
}
