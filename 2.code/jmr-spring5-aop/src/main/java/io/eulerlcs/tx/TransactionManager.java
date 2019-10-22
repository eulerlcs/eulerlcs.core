package io.eulerlcs.tx;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TransactionManager {
	@Pointcut("execution( * io.eulerlcs.service.*Service.*(..))")
	private void txPoint() {
	}

	@Before("txPoint()")
	public void begin() {
		System.out.println("transaction begin");
	}

	@AfterReturning("txPoint()")
	public void commit() {
		System.out.println("transaction commit");
	}

	@AfterThrowing("txPoint()")
	public void rollback() {
		System.out.println("transaction rollback");
	}
}
