package com.github.eulerlcs.challenge.classloader.sampleRename;

import com.github.eulerlcs.challenge.classloader.core.ICalculator;

public class CalculatorBasic implements ICalculator {

	public String calculate(String expression) {
		return expression;
	}

	public String getVersion() {
		return "1.0";
	}

}
