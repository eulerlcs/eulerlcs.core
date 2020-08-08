package com.github.eulerlcs.study.s5.ioc.hello.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;

import com.github.eulerlcs.study.s5.ioc.hello.core.BeanFactory;
import com.github.eulerlcs.study.s5.ioc.hello.service.impl.SomeServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class SomeServiceTest {

	@Test
	void testNew() {
		SomeService someService = new SomeServiceImpl();
		String output = someService.find("xxx");
		log.info("the result of someService.find() = [" + output + "]");
	}

	@Test
	void testBeanFactory() throws BeansException {
		SomeService someService = (SomeService) BeanFactory.getBean("someService");
		String output = someService.find("xxx");
		log.info("the result of someService.find() = [" + output + "]");
	}

	@Test
	void testBeanFactory2() throws BeansException {
		SomeService someService = BeanFactory.getBean("someService", SomeService.class);
		String output = someService.find("xxx");
		log.info("the result of someService.find() = [" + output + "]");
	}
}
