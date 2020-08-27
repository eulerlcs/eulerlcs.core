package com.github.eulerlcs.study.s5.ioc.hello.service.impl;

import com.github.eulerlcs.study.s5.ioc.hello.dao.impl.SomeDaoImpl;
import com.github.eulerlcs.study.s5.ioc.hello.service.SomeService;

public class SomeServiceImpl implements SomeService {

	@Override
	public String find(String input) {
		SomeDaoImpl someDaoImpl = new SomeDaoImpl();
		return someDaoImpl.find(input);
	}

}
