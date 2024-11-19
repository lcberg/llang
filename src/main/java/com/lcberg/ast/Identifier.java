package com.lcberg.ast;

import com.lcberg.token.Token;

public class Identifier implements Expression {
	Token token;
	String value;

	public String expressionNode() {
		return "";
	}

	public String TokenLiteral() {
		return token.Literal;
	}
}
