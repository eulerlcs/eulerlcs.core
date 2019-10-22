package io.eulerlcs.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import io.eulerlcs.tx.TransactionManager;

@Component
public class EmployeeServiceJdkProxy implements InvocationHandler {

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private TransactionManager tx;

	@Autowired
	@Qualifier("employeeService")
	private Object target;

	@SuppressWarnings("unchecked")
	public <T> T getProxyObject() {
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
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
