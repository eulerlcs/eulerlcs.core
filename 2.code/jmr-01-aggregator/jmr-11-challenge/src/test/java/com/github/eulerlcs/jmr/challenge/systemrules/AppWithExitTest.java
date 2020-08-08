/* copy from http://stefanbirkner.github.io/system-rules/index.html */

package com.github.eulerlcs.jmr.challenge.systemrules;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import com.github.eulerlcs.challenge.systemrules.AppWithExit;

public class AppWithExitTest {
	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	@Test
	public void exits() {
		exit.expectSystemExit();
		AppWithExit.doSomethingAndExit();
	}

	@Test
	public void exitsWithStatusCode1() {
		exit.expectSystemExitWithStatus(1);
		AppWithExit.doSomethingAndExit();
	}

	@Test
	public void writesMessage() {
		exit.expectSystemExitWithStatus(1);
		// todo v1.16.1
		// exit.checkAssertionAfterwards(new Assertion() {
		// public void checkAssertion() {
		// assertEquals("exit ...", AppWithExit.message);
		// }
		// });
		AppWithExit.doSomethingAndExit();
	}

	@Test
	public void systemExitWithStatusCode1() {
		exit.expectSystemExitWithStatus(1);
		AppWithExit.doSomethingAndExit();
	}

	@Test
	public void noSystemExit() {
		AppWithExit.doNothing();
		// passes
	}
}