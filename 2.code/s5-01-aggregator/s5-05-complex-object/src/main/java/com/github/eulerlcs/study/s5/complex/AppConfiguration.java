package com.github.eulerlcs.study.s5.complex;

import java.sql.Connection;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

	@Bean("conn")
	public FactoryBean<Connection> getMysqlConnection() {
		return new ConnectFactoryBean();
	}

	@Bean("conn2")
	public Connection getMysqlConnection2() throws Exception {
		return OrignalFactory.getConnection();
	}
}
