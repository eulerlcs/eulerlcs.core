package com.example.demo.e2e;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import io.karatelabs.core.Runner;
import io.karatelabs.core.SuiteResult;

class KarateE2ETest {

  @Test
  @EnabledIfSystemProperty(named = "runE2E", matches = "true")
  void runE2E() {
    SuiteResult result = Runner.path("classpath:features/users-e2e.feature")
        .outputHtmlReport(true)
        .parallel(1);

    assertTrue(result.isPassed());
  }
}
