package io.eulerlcs.dao;

import org.springframework.stereotype.Repository;

import io.eulerlcs.domain.Employee;
import lombok.SneakyThrows;

@Repository
public class EmployeeDao implements IEmployeeDao {

	@Override
	public void save(Employee employee) {
		System.out.println("EmployeeDao save success");
	}

	@Override
	@SneakyThrows
	public void update(Employee employee) {
		System.out.println("EmployeeDao update");
		throw new RuntimeException("EmployeeDao update fail");
	}
}
