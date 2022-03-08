package io.github.djerohn.matcher.json;

public enum JsonCompareMode {
	/**
	 * Количество полей в объектах должно совпадать, порядок следования объектов в массиве должен быть строгим
	 */
	STRICT(false, true, false),

	/**
	 * Количество полей в объектах может не совпадать, порядок следования объектов
	 * в массиве может быть не строгим
	 */
	LENIENT(true, false, false),

	/**
	 * Количество полей в объектах должно совпадать, порядок следования объектов в массиве может быть не строгим
	 */
	NON_EXTENSIBLE(false, false, false),

	/**
	 * Количество полей в объектах может не совпадать, порядок следования объектов
	 * в массиве должен быть строгим
	 */
	STRICT_ORDER(true, true, false),

	/**
	 * Количество полей в объектах должно совпадать, порядок следования объектов в массиве может быть не строгим
	 * числа сравниваются математически, а не по типу
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
