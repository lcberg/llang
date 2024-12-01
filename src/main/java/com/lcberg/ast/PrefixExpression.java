package com.lcberg.ast;

import com.lcberg.token.Token;

public class PrefixExpression implements Expression {
	public Token token;
	public String operator;
	public Expression right;

	public PrefixExpression(Token token, String operator) {
		this.token = token;
		this.operator = operator;
	}

	public String expressionNode() {
		return "";
	}

	public String TokenLiteral() {
		return this.token.Literal;
	}

	public String String() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("(");
		stringBuilder.append(this.operator);
		stringBuilder.append(this.right.String());
		stringBuilder.append(")");

		return stringBuilder.toString();
	}
}
