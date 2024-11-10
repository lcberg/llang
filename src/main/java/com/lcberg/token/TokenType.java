package com.lcberg.token;

public enum TokenType {
	ILLEGAL("ILLEGAL"),
	EOF("EOF"),

	// Identifiers + literals
	IDENT("IDENT"),
	INT("INT"),

	// Operators
	ASSIGN("="),
	PLUS("+"),
	MINUS("-"),

	// DELIMITERS
	COMMA(","),
	SEMICOLON(";"),

	LPAREN("("),
	RPAREN(")"),
	LBRACE("{"),
	RBRACE("}"),

	// KEYWORDS
	FUNCTION("FUNCTION"),
	LET("LET");

	public final String label;

	private TokenType(String label) {
		this.label = label;
	}
}
