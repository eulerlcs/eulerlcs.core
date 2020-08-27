package com.github.eulerlcs.study.s5.complex;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class ConnectFactoryBeanTest {

	@Test
	void testFactoryBean() {
		String[] basePackages = { "com.github.eulerlcs.study.s5.complex" };

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(basePackages)) {
			// ctx.scan("com.github.eulerlcs.study.s5.complex");
			// ctx.register(AppConfiguration.class);
			// ctx.refresh();
			Object conn = ctx.getBean("conn");
			log.info(conn.toString());
		}
	}

	@Test
	void testFactoryBean2() {
		String[] basePackages = { "com.github.eulerlcs.study.s5.complex" };

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(basePackages)) {
			// ctx.scan("com.github.eulerlcs.study.s5.complex");
			// ctx.register(AppConfiguration.class);
			// ctx.refresh();
			Object conn = ctx.getBean("conn2");
			log.info(conn.toString());
		}
	}

}
