package com.lcberg.parser;

public enum Precedence {
	NONE(0),
	LOWEST(1),
	EQUALS(2), // ==
	LESSGREATER(3), // > or <
	SUM(4), // +
	PRODUCT(5), // *
	PREFIX(6), // -X or !X
	CALL(7); // myFunction(X)

	private final int value;

	Precedence(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
