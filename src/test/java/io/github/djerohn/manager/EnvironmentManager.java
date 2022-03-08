package io.github.djerohn.manager;

import io.github.djerohn.configuration.EnvironmentConfigurationLoader;
import io.github.djerohn.environment.configs.EnvironmentConfig;
import io.github.djerohn.environment.Environments;
import io.github.djerohn.exception.ApiTestException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnvironmentManager {
	private static final EnvironmentConfigurationLoader CONFIG_LOADER = new EnvironmentConfigurationLoader();
	private static final Environments ENVIRONMENTS_CONFIGURATION = CONFIG_LOADER.loadConfiguration(
			Optional.ofNullable(System.getProperty("config.path"))
					.orElse("test-env.yml"), Environments.class);

	public static EnvironmentConfig getEnvironmentVariables() {
		return Optional.ofNullable(ENVIRONMENTS_CONFIGURATION.getEnvironmentConfigs().get(Optional.ofNullable(System.getProperty("env")).orElse("default")))
				.orElseThrow(() ->
						new ApiTestException("Environment configuration haven't been set up. Set environment variables in env.default or env.${env}"));
	}
}