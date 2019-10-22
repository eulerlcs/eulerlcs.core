package io.eulerlcs.service;

import io.eulerlcs.domain.Employee;

public interface IEmployeeService {
	void save(Employee employee);

	void update(Employee employee);
}
