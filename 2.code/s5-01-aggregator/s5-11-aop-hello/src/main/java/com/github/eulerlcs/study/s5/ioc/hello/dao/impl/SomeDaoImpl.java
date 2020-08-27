package com.github.eulerlcs.study.s5.ioc.hello.dao.impl;

import com.github.eulerlcs.study.s5.ioc.hello.dao.SomeDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SomeDaoImpl implements SomeDao {

	@Override
	public String find(String input) {
		String sql = "select info from some_table where some_field = " + input;
		log.info(sql);
		return "some_info_from_db";
	}

}
