package io.eulerlcs.dao;

import io.eulerlcs.domain.Employee;

public interface IEmployeeDao {
	void save(Employee employee);

	void update(Employee employee);
}
