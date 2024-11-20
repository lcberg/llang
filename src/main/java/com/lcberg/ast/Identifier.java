package com.lcberg.ast;

import com.lcberg.token.Token;

public class Identifier implements Expression {
	public Token token;
	public String value;

	public Identifier(Token token, String value) {
		this.token = token;
		this.value = value;
	}

	public String expressionNode() {
		return "";
	}

	public String TokenLiteral() {
		return token.Literal;
	}
}
