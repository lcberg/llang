package com.lcberg.ast;

import com.lcberg.token.Token;

public class IfExpression implements Expression {
	public Token token;
	public Expression condition;
	public BlockStatement consequence;
	public BlockStatement alternative;

	public IfExpression(Token token) {
		this.token = token;
	}

	public String TokenLiteral() {
		return this.token.Literal;
	}

	public String String() {
		StringBuilder sb = new StringBuilder();
		sb.append("if");
		sb.append(this.condition.String());
		sb.append(" ");
		sb.append(this.consequence.String());

		if (this.alternative != null) {
			sb.append("else ");
			sb.append(this.alternative.String());
		}

		return sb.toString();
	}

	public String expressionNode() {
		return "";
	}
}
