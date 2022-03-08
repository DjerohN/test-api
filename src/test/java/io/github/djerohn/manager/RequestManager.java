package io.github.djerohn.manager;

import io.github.djerohn.environment.configs.ApplicationConfig;
import io.github.djerohn.exception.ApiTestException;
import io.github.djerohn.request.RequestData;
import io.github.djerohn.request.RequestMethod;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestManager {

	public static Response sendRequest(RequestMethod method, String application, RequestData requestData) {
		switch (method) {
			case GET -> {
				RequestSpecification requestSpecification = RequestManager.constructRequest(application, requestData);
				return RestAssured.given()
						.spec(requestSpecification)
						.when()
						.redirects().follow(false)
						.get();
			}
			case POST -> {
				RequestSpecification requestSpecification = RequestManager.constructRequestWithPayload(application, requestData);
				return RestAssured.given()
						.spec(requestSpecification)
						.when()
						.redirects().follow(false)
						.post();
			}
			case PUT -> {
				RequestSpecification requestSpecification = RequestManager.constructRequestWithPayload(application, requestData);
				return RestAssured.given()
						.spec(requestSpecification)
						.when()
						.redirects().follow(false)
						.put();
			}
			case PATCH -> {
				RequestSpecification requestSpecification = RequestManager.constructRequestWithPayload(application, requestData);
				return RestAssured.given()
						.spec(requestSpecification)
						.when()
						.redirects().follow(false)
						.patch();
			}
			case DELETE -> {
				RequestSpecification requestSpecification = RequestManager.constructRequestWithPayload(application, requestData);
				return RestAssured.given()
						.spec(requestSpecification)
						.when()
						.redirects().follow(false)
						.delete();
			}
			default -> throw new ApiTestException(method + "is now valid request method! Valid methods are " +
					Arrays.stream(RequestMethod.values()).map(RequestMethod::toString).collect(Collectors.joining(", ")));
		}
	}

	public static RequestSpecification constructRequest(String application, RequestData requestData) {
		RequestSpecBuilder requestBuilder = getRequestBuilder(application, requestData);

		return requestBuilder.build();
	}

	public static RequestSpecification constructRequestWithPayload(String application, RequestData requestData) {
		RequestSpecBuilder requestBuilder = getRequestBuilder(application, requestData);
		String requestBody = Optional.ofNullable(requestData.getRequestBody()).orElse("");
		requestBuilder.setBody(requestBody);

		return requestBuilder.build();
	}

	private static RequestSpecBuilder getRequestBuilder(String application, RequestData requestData) {
		String host = getApplicationConfig(application).getHost();
		String port = getApplicationConfig(application).getPort();
		String path = requestData.getUlrPath();
		Map<String, List<String>> queryParams = requestData.getQueryParams();
		Map<String, List<String>> formParams = requestData.getFormParams();
		Map<String, String> headers = requestData.getHeaderMap();

		RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
		requestBuilder.setBaseUri(StringUtils.isNotBlank(host) ? host : "");
		if (StringUtils.isNotBlank(port)) {
			requestBuilder.setPort(Integer.parseInt(port));
		}
		requestBuilder.setBasePath(StringUtils.isNotBlank(path) ? path : "");
		requestBuilder.addQueryParams(MapUtils.isNotEmpty(queryParams) ? queryParams : new HashMap<>());
		requestBuilder.addFormParams(MapUtils.isNotEmpty(formParams) ? formParams : new HashMap<>());
		requestBuilder.addHeaders(MapUtils.isNotEmpty(headers) ? headers : new HashMap<>());
		requestBuilder.addFilter(new AllureRestAssured());

		return requestBuilder;
	}

	private static ApplicationConfig getApplicationConfig(String application) {
		return Optional.ofNullable(EnvironmentManager.getEnvironmentVariables().getApplicationConfigs().get(application))
				.orElseThrow(() ->
						new NoSuchElementException("Application " + application + " haven't been set up. Check test-env.yml and set environment variables ${env}.application.${application}"));
	}
}
