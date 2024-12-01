package com.lcberg.ast;

import com.lcberg.token.Token;

public class InfixExpression implements Expression {
	public Token token;
	public Expression left;
	public String operator;
	public Expression right;

	public InfixExpression(Token token, String operator, Expression left) {
		this.token = token;
		this.operator = operator;
		this.left = left;
	}

	public String expressionNode() {
		return "";
	}

	public String String() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(left.String());
		sb.append(" ");
		sb.append(this.operator);
		sb.append(" ");
		sb.append(right.String());
		sb.append(")");
		return sb.toString();
	}

	public String TokenLiteral() {
		return this.token.Literal;
	}
}
