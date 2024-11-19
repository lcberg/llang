package com.lcberg.token;

import java.util.Map;

public class Token {
	public TokenType Type;
	public String Literal;

	private static Map<String, TokenType> keywords = Map.of(
			"let", TokenType.LET,
			"fn", TokenType.FUNCTION,
			"if", TokenType.IF,
			"else", TokenType.ELSE,
			"return", TokenType.RETURN,
			"true", TokenType.TRUE,
			"false", TokenType.FALSE);

	public Token(TokenType type, String literal) {
		this.Type = type;
		this.Literal = literal;
	}

	public static TokenType lookupIdentifier(String identifier) {
		if (keywords.containsKey(identifier))
			return keywords.get(identifier);
		return TokenType.IDENT;
	}

	@Override
	public String toString() {
		return "[" + Type + "] " + Literal;
	}
}
