package io.github.djerohn.request;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class RequestData {
	private String ulrPath;
	private Map<String, List<String>> queryParams = new HashMap<>();
	private Map<String, List<String>> formParams = new HashMap<>();
	private Map<String, String> headerMap = new HashMap<>();
	private String requestBody;
}
