package io.eulerlcs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.eulerlcs.dao.IEmployeeDao;
import io.eulerlcs.domain.Employee;

@Service
public class EmployeeService implements IEmployeeService {
	@Autowired
	private IEmployeeDao employeeDao;

	@Override
	public void save(Employee employee) {
		employeeDao.save(employee);
		System.out.println("EmployeeService save success");
	}

	@Override
	public void update(Employee employee) {
		employeeDao.update(employee);
		System.out.println("EmployeeService update success");
	}
}
