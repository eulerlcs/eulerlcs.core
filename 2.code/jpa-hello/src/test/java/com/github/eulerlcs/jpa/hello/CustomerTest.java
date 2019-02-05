package com.github.eulerlcs.jpa.hello;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.eulerlcs.jpa.hello.Customer;

public class CustomerTest {

	private String persistenceUnitName;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;

	@Before
	public void setUp() throws Exception {
		persistenceUnitName = "jpa-2";

		entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
		entityManager = entityManagerFactory.createEntityManager();

		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
	}

	@After
	public void tearDown() throws Exception {
		entityTransaction.commit();

		entityManager.close();

		entityManagerFactory.close();
	}

	@Test
	public void test() {
		Customer customer = new Customer();
		customer.setAge(12);
		customer.setEmail("tom@euler.com");
		customer.setLastName("Tom");

		entityManager.persist(customer);
	}
}
