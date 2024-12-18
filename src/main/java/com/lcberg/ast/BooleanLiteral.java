package com.lcberg.ast;

import com.lcberg.token.Token;

public class BooleanLiteral implements Expression {
	public Token token;
	public Boolean value;

	public BooleanLiteral(Token token, Boolean value) {
		this.token = token;
		this.value = value;
	}

	public String TokenLiteral() {
		return this.token.Literal;
	}

	public String String() {
		return this.token.Literal;
	}

	public String expressionNode() {
		return "";
	}

}
