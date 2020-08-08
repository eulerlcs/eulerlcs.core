package io.eulerlcs.proxy.staic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import io.eulerlcs.domain.Employee;
import io.eulerlcs.service.IEmployeeService;

@SpringJUnitConfig(locations = "classpath:io/eulerlcs/eulerlcs.xml")
class EmployeeServiceStaticProxyTest {

	@Autowired
	@Qualifier("employeeServiceStaticProxy")
	private IEmployeeService service;

	@Test
	void testSave() {
		System.out.println(service);
		service.save(new Employee());
	}

	@Test
	void testUpdate() {
		service.update(new Employee());
	}

}
