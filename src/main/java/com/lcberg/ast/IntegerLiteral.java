package com.lcberg.ast;

import com.lcberg.token.Token;

public class IntegerLiteral implements Expression {
	public int value;
	public Token token;

	public IntegerLiteral(int value, Token token) {
		this.value = value;
		this.token = token;
	}

	public String expressionNode() {
		return "";
	}

	public String TokenLiteral() {
		return this.token.Literal;
	}

	public String String() {
		return this.token.Literal;
	}

}
