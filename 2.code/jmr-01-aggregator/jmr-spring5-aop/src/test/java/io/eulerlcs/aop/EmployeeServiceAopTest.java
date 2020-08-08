package io.eulerlcs.aop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import io.eulerlcs.domain.Employee;
import io.eulerlcs.service.IEmployeeService;

@SpringJUnitConfig(locations = "classpath:io/eulerlcs/aop.xml")
class EmployeeServiceAopTest {

	@Autowired
	@Qualifier("employeeService")
	private IEmployeeService service;

	@Test
	void testToString() {
		System.out.println(service.toString());
		System.out.println("-----------------------");
	}

	@Test
	void testSave() {
		System.out.println(service.getClass());
		service.save(new Employee());
		System.out.println("-----------------------");
	}

	@Test
	void testUpdate() {
		service.update(new Employee());
		System.out.println("-----------------------");
	}
}
