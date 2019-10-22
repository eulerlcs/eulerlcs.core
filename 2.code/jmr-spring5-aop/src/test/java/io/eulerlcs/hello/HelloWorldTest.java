package io.eulerlcs.hello;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "classpath:io/eulerlcs/hello.xml")
public class HelloWorldTest {

	@Autowired
	@Qualifier("helloWorld")
	private HelloWorld helloWorld;

	@Test
	void testToString() {
		System.out.println(helloWorld);
	}
}
