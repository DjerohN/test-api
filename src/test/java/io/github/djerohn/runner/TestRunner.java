package io.github.djerohn.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", "pretty"},
		features = "classpath:features",
		glue = {"io.github.djerohn.stepdefinitions",
				"io.github.djerohn.configuration",
				"io.github.djerohn.hooks"
		},
		tags = "@gorest-co-in"
)

public class TestRunner {
}
