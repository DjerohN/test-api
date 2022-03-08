package io.github.djerohn.manager;

import io.github.djerohn.exception.ApiTestException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataManager {
	private static final String TEST_DATA_DIRECTORY = EnvironmentManager.getEnvironmentVariables().getTestDataDirectory();

	public static String getTestFileData(String relativeFilePath) {
		try (InputStream fileInputStream = Optional.ofNullable(DataManager.class.getClassLoader().getResourceAsStream(TEST_DATA_DIRECTORY + File.separator + relativeFilePath))
				.orElseThrow(() -> new ApiTestException(String.format("File %s does not exist!", relativeFilePath)))) {
			return IOUtils.toString(fileInputStream, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new ApiTestException(e);
		}
	}
}
