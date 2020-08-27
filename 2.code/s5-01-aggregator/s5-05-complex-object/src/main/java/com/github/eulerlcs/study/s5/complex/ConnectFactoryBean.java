package com.github.eulerlcs.study.s5.complex;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.FactoryBean;

/**
 * 接口+反射，什么都能做 * 缺点： 对Spring产生了依赖
 * 
 * @author lcs43
 *
 */
public class ConnectFactoryBean implements FactoryBean<Connection> {

	@Override
	public Connection getObject() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:33061/mybatis3?useSSL=false&serverTimezone=JST";
		Connection connection = DriverManager.getConnection(url, "user01", "123456");

		return connection;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return Connection.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
