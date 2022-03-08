package io.github.djerohn.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.djerohn.auth.Authorization;
import io.github.djerohn.exception.ApiTestException;
import io.github.djerohn.manager.DataManager;
import io.github.djerohn.manager.RequestManager;
import io.github.djerohn.manager.SessionManager;
import io.github.djerohn.matcher.json.JsonCompareMode;
import io.github.djerohn.matcher.json.JsonMatcherHelper;
import io.github.djerohn.request.RequestData;
import io.github.djerohn.request.RequestMethod;
import io.github.djerohn.session.Session;
import io.github.djerohn.util.ApiTestUtil;
import io.qameta.allure.Allure;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ApiStepDefinitions {
	private Session session;
	private RequestData requestData = new RequestData();
	private Response response;

	public ApiStepDefinitions(Session session) {
		this.session = session;
	}

	@Given("endpoint will be \"{}\"")
	public void userPlansCallMethod(String endpoint) {
		requestData.setUlrPath(SessionManager.setSessionVariables(session, endpoint));
	}

	@And("query params will be:")
	public void queryParamsWillBe(Map<String, List<String>> queryParams) {
		requestData.setQueryParams(queryParams.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, values -> values.getValue().stream()
						.filter(Objects::nonNull)
						.collect(Collectors.toList())
				)));
	}

	@And("form params will be:")
	public void formParamsWillBe(Map<String, List<String>> formParams) {
		requestData.setFormParams(formParams.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, values -> values.getValue().stream()
						.filter(Objects::nonNull)
						.collect(Collectors.toList())
				)));
	}

	@And("^(?:.*) authorized with token \"(.*)\"$")
	public void auth(String tokenPath) {
		if (requestData.getHeaderMap() != null) {
			requestData.getHeaderMap().putAll(Authorization.setAuthHeaderWithToken(tokenPath));
		} else {
			requestData.setHeaderMap(Authorization.setAuthHeaderWithToken(tokenPath));
		}
	}

	@And("headers will be:")
	public void requestContainsHeaders(Map<String, String> headers) {
		if (requestData.getHeaderMap() != null) {
			requestData.getHeaderMap().putAll(headers);
		} else {
			requestData.setHeaderMap(headers);
		}
	}

	@And("request body will be \"{}\"")
	public void setRequestBody(String requestBodyPath) {
		String requestBody = DataManager.getTestFileData(requestBodyPath);
		Allure.attachment("Request Body", requestBody);
		requestData.setRequestBody(requestBody);
	}

	@When("^(?:.*) sends (.*) request in application \"(.*)\"$")
	public void userSendsRequest(RequestMethod method, String application) {
		response = RequestManager.sendRequest(method, application, requestData);
		requestData = new RequestData();
	}

	@Then("response code is equal to \"{}\"")
	public void responseCodeShouldBe(int code) {
		ApiTestUtil.checkResponseExistence(response);
		MatcherAssert.assertThat(
				response.then().extract().statusCode(),
				Matchers.is(code)
		);
	}

	@And("response equals to JSON-scheme \"{}\"")
	public void validateResponseWithJsonSchema(String jsonSchemaPath) {
		ApiTestUtil.checkResponseExistence(response);
		String jsonSchema = DataManager.getTestFileData(jsonSchemaPath);
		Allure.attachment("JSON Schema", jsonSchema);
		response.then()
				.assertThat()
				.body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
	}

	@And("JSON response body is equal to \"{}\" by comparison rule \"{}\"")
	public void verifyResponseJson(String expectedJsonPath, JsonCompareMode jsonCompareMode) {
		ApiTestUtil.checkResponseExistence(response);
		String expectedResult = SessionManager.setSessionVariables(session, DataManager.getTestFileData(expectedJsonPath));
		String actualResult = response.then().extract().body().asString();

		Allure.attachment("Expected JSON", expectedResult);

		MatcherAssert.assertThat(
				actualResult,
				JsonMatcherHelper.prepareMatcher(expectedResult, jsonCompareMode)
		);
	}

	@And("response body is empty")
	public void verifyEmptyResponse() {
		ApiTestUtil.checkResponseExistence(response);
		response.then().assertThat().body(Matchers.emptyString());
	}

	@And("response headers are equal to:")
	public void verifyResponseHeaders(Map<String, List<String>> expectedHeaders) {
		ApiTestUtil.checkResponseExistence(response);
		List<Header> processedExpectedHeaders = expectedHeaders.entrySet().stream()
				.flatMap(entry -> entry.getValue().stream()
						.filter(Objects::nonNull)
						.map(value -> new Header(entry.getKey(), value))
				)
				.collect(Collectors.toList());

		List<Header> actualHeaders = response.then().extract().headers().asList();

		Assertions.assertThat(actualHeaders).containsAll(processedExpectedHeaders);
	}

	@And("variable \"{}\" gets value of field \"{}\" from response JSON")
	public void saveVariableFromJsonResponse(String varName, String jsonPath) {
		ApiTestUtil.checkResponseExistence(response);
		JsonPathConfig config = new JsonPathConfig().charset("UTF-8").numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL);
		String varValue = Optional.ofNullable(response.then()
						.extract()
						.response()
						.jsonPath(config)
						.getString(jsonPath))
				.orElseThrow(() -> new ApiTestException("Nothing found at the specified JSON path: " + jsonPath + ". Set correct JSON path"));

		varValue = varValue.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\\\\\n").replace("\"", "\\\\\\\"");

		Allure.attachment("Session variable", varName + " = " + varValue);
		session.getSessionMap().put(varName, varValue);
	}
}
