package io.github.djerohn.matcher.json;

public enum JsonCompareMode {
	/**
	 * Number of fields must be equal, fields must be in specific order
	 */
	STRICT(false, true, false),

	/**
	 * Number of fields may not be equal, fields may be unordered
	 */
	LENIENT(true, false, false),

	/**
	 * Number of fields must be equal, fields may be unordered
	 */
	NON_EXTENSIBLE(false, false, false),

	/**
	 * Number of fields may not be equal, fields must be in specific order
	 */
	STRICT_ORDER(true, true, false),

	/**
	 * Number of fields must be equal, fields may be unordered
	 * Numbers are compared mathematically, not by type
	 */
	WITH_TOLERANCE(false, false, true);

	private final boolean extensible;
	private final boolean strictOrder;
	private final boolean tolerant;

	public boolean isExtensible() {
		return extensible;
	}

	public boolean hasStrictOrder() {
		return strictOrder;
	}

	public boolean isTolerant() {
		return tolerant;
	}

	JsonCompareMode(boolean extensible, boolean strictOrder, boolean tolerant) {
		this.extensible = extensible;
		this.strictOrder = strictOrder;
		this.tolerant = tolerant;
	}
}
