package io.eulerlcs.proxy.staic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.eulerlcs.domain.Employee;
import io.eulerlcs.service.IEmployeeService;
import io.eulerlcs.tx.TransactionManager;

@Service
public class EmployeeServiceStaticProxy implements IEmployeeService {

	@Autowired
	@Qualifier("employeeService")
	private IEmployeeService target;
	@Autowired
	private TransactionManager tx;

	@Override
	public void save(Employee employee) {
		tx.begin();

		try {
			target.save(employee);
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
		}
	}

	@Override
	public void update(Employee employee) {
		tx.begin();

		try {
			target.update(employee);
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
		}
	}
}
