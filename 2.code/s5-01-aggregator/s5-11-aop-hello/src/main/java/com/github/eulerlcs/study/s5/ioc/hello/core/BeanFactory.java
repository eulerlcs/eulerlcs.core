package com.github.eulerlcs.study.s5.ioc.hello.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanFactory {
	private static Properties env = new Properties();

	static {

		try (InputStream is = BeanFactory.class.getClassLoader()
				.getResourceAsStream(BeanFactory.class.getSimpleName() + ".properties")) {
			env.load(is);
		} catch (IOException e) {
			log.error("error at load BeanFactory", e);
		}
	}

	public static Object getBean(String name) throws BeansException {
		String beanName = env.getProperty(name);
		Object instance;
		try {
			Class<?> clazz = Class.forName(beanName);
			instance = clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new NoSuchBeanDefinitionException(beanName, "bean is not defined");
		}

		return instance;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		Object bean = getBean(name);
		if (requiredType != null && !requiredType.isInstance(bean)) {
			throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
		}

		return (T) bean;
	}
}
