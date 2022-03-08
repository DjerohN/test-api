package io.github.djerohn.manager;

import io.github.djerohn.session.Session;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionManager {

	private static final Pattern VARIABLE_PARAMETER_PATTERN = Pattern.compile("\\{\\{((?:(?!}}|\\{\\{).)+)}}");

	public static String setSessionVariables(Session session, String value) {
		if (value != null) {
			Matcher matcher = VARIABLE_PARAMETER_PATTERN.matcher(value);
			StringBuffer buffer = null;

			while (matcher.find()) {
				if (buffer == null) {
					buffer = new StringBuffer();
				}

				if (session.getSessionMap().containsKey(matcher.group(1))) {
					matcher.appendReplacement(buffer, session.getSessionMap().get(matcher.group(1)).toString());
				} else {
					log.debug("Session has no variable called " + matcher.group(1));
				}
			}

			if (buffer != null) {
				value = matcher.appendTail(buffer).toString();
			}
		}
		return value;
	}
}
