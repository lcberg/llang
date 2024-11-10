package com.lcberg.token;

import java.util.Map;

public class Token {
	public TokenType Type;
	public String Literal;

	private static Map<String, TokenType> keywords = Map.of(
			"let", TokenType.LET,
			"fn", TokenType.FUNCTION);

	public Token(TokenType type, String literal) {
		this.Type = type;
		this.Literal = literal;
	}

	public static TokenType lookupIdentifier(String identifier) {
		if (keywords.containsKey(identifier))
			return keywords.get(identifier);
		return TokenType.IDENT;
	}
}
