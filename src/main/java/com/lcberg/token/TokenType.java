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
	BANG("!"),
	ASTERISK("*"),
	SLASH("/"),
	LESS_THAN("<"),
	GREATER_THAN(">"),
	EQUALS("=="),
	NOT_EQUALS("!="),

	// DELIMITERS
	COMMA(","),
	SEMICOLON(";"),

	LPAREN("("),
	RPAREN(")"),
	LBRACE("{"),
	RBRACE("}"),

	// KEYWORDS
	FUNCTION("FUNCTION"),
	LET("LET"),
	RETURN("RETURN"),
	TRUE("TRUE"),
	FALSE("FALSE"),
	ELSE("ELSE"),
	IF("IF");

	public final String label;

	private TokenType(String label) {
		this.label = label;
	}
}
