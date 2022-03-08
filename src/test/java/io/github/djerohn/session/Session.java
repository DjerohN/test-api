package io.github.djerohn.session;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Session {
	private Map<Object, Object> sessionMap = new HashMap<>();
}
