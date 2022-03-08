package io.github.djerohn.request;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RequestData {
	private String ulrPath;
	private Map<String, List<String>> queryParams;
	private Map<String, List<String>> formParams;
	private Map<String, String> headerMap;
	private String requestBody;
}
