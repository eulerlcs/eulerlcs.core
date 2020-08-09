package com.github.eulerlcs.study.s5.junit5;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class SomeTest {

	@ParameterizedTest
	@ValueSource(strings = { "p1", "p2", "p3" })
	@DisplayName("testGetBeanByIdType")
	void testGetBeanByIdType(String input) {
		log.info("the result of someService.find() = [" + input + "]");
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/sample.csv", numLinesToSkip = 1)
	void testByCsv(String id, String name) {
		log.info("id=[{}], name=[{}]", id, name);
	}

	@ParameterizedTest
	@MethodSource("getUserInfo")
	void testByMethod(String name) {
		log.info(" name=[{}]", name);
	}

	static Stream<String> getUserInfo() {

		return Stream.of("stream1", "stream2");
	}

	@ParameterizedTest
	@CsvSource({ "2019-01-01,  1, 2019-01-02", "2019-01-01, 30, 2019-01-31", "2019-01-01, 40, 2019-02-10", })
	void testWithCsvSource(String baseDateText, int amountToAdd, String expectedText) {

		LocalDate actual = LocalDate.parse(baseDateText).plusDays(amountToAdd);
		LocalDate expected = LocalDate.parse(expectedText);

		// assertThat(actual).isEqualTo(expected);
		Assertions.assertEquals(expected, actual);
	}
}
