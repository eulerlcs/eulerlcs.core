package io.eulerlcs.proxy.cglib;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import io.eulerlcs.domain.Employee;
import io.eulerlcs.service.IEmployeeService;

@SpringJUnitConfig(locations = "classpath:io/eulerlcs/eulerlcs.xml")
class EmployeeServiceCglibProxyTest {

	@Autowired
	private EmployeeServiceCglibProxy proxy;

	static {
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "d:/javadebug");
	}

	@Test
	void testToString() {
		IEmployeeService employeeService = proxy.getProxyObject();
		System.out.println(employeeService.toString());
	}

	@Test
	void testSave() {
		IEmployeeService employeeService = proxy.getProxyObject();
		System.out.println(employeeService.getClass());
		employeeService.save(new Employee());
	}

	@Test
	void testUpdate() {
		IEmployeeService employeeService = proxy.getProxyObject();
		employeeService.update(new Employee());
	}
}
