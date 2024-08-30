package com.example.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogAop {

	@Pointcut("within(com.example.demo.*)")
	private void pointcutMethod() {
	}

	@Around("pointcutMethod()")
	public Object loggerAop(ProceedingJoinPoint jointpoint) throws Throwable {
		String signature = jointpoint.getSignature().toShortString();
		System.out.println(signature + "is start.");
		long start = System.currentTimeMillis();

		try {
			Object obj = jointpoint.proceed();
			return obj;
		} finally {
			long end = System.currentTimeMillis();
			System.out.println(signature + " is finished.");
			System.out.println(signature + " 경과시간 : " + (end - start));
		}
	}

//	@Before("within(com.example.demo.*)")
//	public void beforeAdvice() {
//		System.out.println("beforeAdvice()");
//	}

}
