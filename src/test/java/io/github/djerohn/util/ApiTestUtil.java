package io.github.djerohn.util;

import io.github.djerohn.exception.ApiTestException;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiTestUtil {
	public static void checkResponseExistence(Response response) {
		if (response == null) {
			throw new ApiTestException("API response is null! Make a request first!");
		}
	}
}
