package com.github.eulerlcs.jmr.algorithm;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArrayList {

	@Test
	public void test_foreach() {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);

		int sum = 0;
		for (Integer item : list) {
			sum += item;
		}

		Assertions.assertEquals(sum, 6);
	}
}
