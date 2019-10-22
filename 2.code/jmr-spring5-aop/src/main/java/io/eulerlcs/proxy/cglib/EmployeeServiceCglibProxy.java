package io.eulerlcs.proxy.cglib;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.stereotype.Component;

import io.eulerlcs.tx.TransactionManager;

@Component
public class EmployeeServiceCglibProxy implements InvocationHandler {
	@Autowired
	private TransactionManager tx;

	@Autowired
	@Qualifier("employeeService")
	private Object target;

	@SuppressWarnings("unchecked")
	public <T> T getProxyObject() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(this);

		return (T) enhancer.create();
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object ret = null;

		tx.begin();

		try {
			ret = method.invoke(target, args);
			tx.commit();
		} catch (Exception ex) {
			tx.rollback();
		}

		return ret;
	}
}
