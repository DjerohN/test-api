package io.github.djerohn.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Authorization {

	public static Map<String, String> setAuthHeaderWithToken(String token) {
		Map<String, String> resultAuthHeader = new HashMap<>();
		resultAuthHeader.put("Authorization", "Bearer " + token);
		return resultAuthHeader;
	}
}
