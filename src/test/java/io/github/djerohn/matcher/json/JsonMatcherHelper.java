package io.github.djerohn.matcher.json;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.javacrumbs.jsonunit.ConfigurableJsonMatcher;
import net.javacrumbs.jsonunit.JsonMatchers;
import net.javacrumbs.jsonunit.core.Option;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonMatcherHelper {
	public static <T> ConfigurableJsonMatcher<T> prepareMatcher(String expectedJson, JsonCompareMode jsonCompareMode) {
		ConfigurableJsonMatcher<T> matcher = JsonMatchers.jsonEquals(expectedJson);
		if (jsonCompareMode.isExtensible()) {
			matcher = matcher.when(Option.IGNORING_EXTRA_FIELDS, Option.IGNORING_EXTRA_ARRAY_ITEMS);
		}
		if (!jsonCompareMode.hasStrictOrder()) {
			matcher = matcher.when(Option.IGNORING_ARRAY_ORDER);
		}
		if (jsonCompareMode.isTolerant()) {
			matcher = matcher.withTolerance(0);
		}
		return matcher;
	}
}
