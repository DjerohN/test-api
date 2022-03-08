package io.github.djerohn.environment.configs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class EnvironmentConfig {
	@JsonProperty("test-data-directory")
	private String testDataDirectory;
	@JsonProperty("application")
	private Map<String, ApplicationConfig> applicationConfigs;
}
