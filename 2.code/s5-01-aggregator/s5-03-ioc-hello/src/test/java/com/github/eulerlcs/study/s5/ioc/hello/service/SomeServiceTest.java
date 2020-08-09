package com.github.eulerlcs.study.s5.ioc.hello.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.BeansException;

import com.github.eulerlcs.study.s5.ioc.hello.core.BeanFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class SomeServiceTest {

	@Test
	@DisplayName("testGetBeanById")
	void testGetBeanById() throws BeansException {
		SomeService someService = (SomeService) BeanFactory.getBean("someService");
		String output = someService.find("xxx");
		log.info("the result of someService.find() = [" + output + "]");
	}

	@ParameterizedTest
	@ValueSource(strings = { "p1", "p2", "p3" })
	@DisplayName("testGetBeanByIdType")
	void testGetBeanByIdType(String input) throws BeansException {
		SomeService someService = BeanFactory.getBean("someService", SomeService.class);
		String output = someService.find(input);
		log.info("the result of someService.find() = [" + output + "]");
	}
}
