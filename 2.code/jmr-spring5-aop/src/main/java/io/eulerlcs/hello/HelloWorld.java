package io.eulerlcs.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Component
public class HelloWorld {
	@Value("${name}")
	private String username;
	@Value("${age}")
	private int age;

	public void sayHello() {
		System.out.println("hello " + username);
	}
}
