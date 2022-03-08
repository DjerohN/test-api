package io.github.djerohn.environment;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.djerohn.environment.configs.EnvironmentConfig;
import lombok.Data;

import java.util.Map;

@Data
public class Environments {
	@JsonProperty("env")
	private Map<String, EnvironmentConfig> environmentConfigs;
}
