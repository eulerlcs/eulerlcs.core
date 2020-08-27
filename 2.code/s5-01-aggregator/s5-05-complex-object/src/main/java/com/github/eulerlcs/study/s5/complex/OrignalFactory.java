package com.github.eulerlcs.study.s5.complex;

import java.sql.Connection;
import java.sql.DriverManager;

public class OrignalFactory {
	public static Connection getConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:33061/mybatis3?useSSL=false&serverTimezone=JST";
		Connection connection = DriverManager.getConnection(url, "user01", "123456");

		return connection;
	}
}
