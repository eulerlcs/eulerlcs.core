package io.eulerlcs.proxy.jdk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import io.eulerlcs.domain.Employee;
import io.eulerlcs.service.IEmployeeService;

@SpringJUnitConfig(locations = "classpath:io/eulerlcs/eulerlcs.xml")
class EmployeeServiceJdkProxyTest {

	@Autowired
	private EmployeeServiceJdkProxy proxy;

	@Test
	void testSave() {
		IEmployeeService employeeService = proxy.getProxyObject();
		employeeService.save(new Employee());
	}

	@Test
	void testUpdate() {
		IEmployeeService employeeService = proxy.getProxyObject();
		employeeService.update(new Employee());
	}
}
