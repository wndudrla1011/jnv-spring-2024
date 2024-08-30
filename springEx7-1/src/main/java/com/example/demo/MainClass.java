package com.example.demo;

import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {

		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(); // Container 생성

		ctx.load("classpath:applicationCTX.xml"); // 설정

		ctx.refresh(); // Bean 등록

		Student student = ctx.getBean("student", Student.class); // 사용
		System.out.println("이름 : " + student.getName());
		System.out.println("나이 : " + student.getAge());

		ctx.close(); // 종료

	}

}
