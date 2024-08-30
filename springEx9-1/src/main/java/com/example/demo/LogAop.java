package com.example.demo;

import org.aspectj.lang.ProceedingJoinPoint;

public class LogAop {

	public Object loggerAop(ProceedingJoinPoint jointpoint) throws Throwable {
		String signatureStr = jointpoint.getSignature().toShortString();
		System.out.println(signatureStr + "is start.");
		long start = System.currentTimeMillis();

		try {
			Object obj = jointpoint.proceed(); // 타겟 메서드 실행
			return obj;
		} finally {
			long end = System.currentTimeMillis();
			System.out.println(signatureStr + "is finished.");
			System.out.println(signatureStr + "경과시간 : " + (end - start));
		}

	}

}
