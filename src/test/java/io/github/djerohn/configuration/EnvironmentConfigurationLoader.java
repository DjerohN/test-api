package io.github.djerohn.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.djerohn.exception.ApiTestException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.lookup.StringLookupFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class EnvironmentConfigurationLoader {
	private final ObjectMapper mapper;
	private final StringSubstitutor stringSubstitutor;

	public EnvironmentConfigurationLoader() {
		this.mapper = ObjectMapperConfiguration.getObjectMapper();
		this.stringSubstitutor = new StringSubstitutor(StringLookupFactory.INSTANCE.systemPropertyStringLookup());
	}

	public <T> T loadConfiguration(String configPath, Class<T> cls) {
		try (InputStream fileInputStream = Optional.ofNullable(EnvironmentConfigurationLoader.class.getClassLoader().getResourceAsStream(configPath))
				.orElseThrow(() -> new ApiTestException("File test-env.yml does not exist. Setup test-env.yml in resources directory or define config.path"))) {
			String contents = this.stringSubstitutor.replace(IOUtils.toString(fileInputStream, StandardCharsets.UTF_8));
			return this.mapper.readValue(contents, cls);
		} catch (IOException e) {
			throw new ApiTestException(e);
		}
	}
}
