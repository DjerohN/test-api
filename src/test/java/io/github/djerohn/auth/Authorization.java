package io.github.djerohn.auth;

import io.github.djerohn.manager.DataManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Authorization {

	public static Map<String, String> setAuthHeaderWithToken(String tokenPath) {
		Map<String, String> resultAuthHeader = new HashMap<>();
		resultAuthHeader.put("Authorization", "Bearer " + DataManager.getTestFileData(tokenPath));
		return resultAuthHeader;
	}
}
