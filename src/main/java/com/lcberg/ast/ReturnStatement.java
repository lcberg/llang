package com.lcberg.ast;

import com.lcberg.token.Token;

public class ReturnStatement implements Statement {
	public Token token;
	public Expression returnValue;

	public ReturnStatement(Token token) {
		this.token = token;
	}

	public String statementNode() {
		return "";
	}

	public String TokenLiteral() {
		return this.token.Literal;
	}

	public String String() {
		StringBuilder sb = new StringBuilder();

		sb.append(TokenLiteral());
		sb.append(" ");
		if (this.returnValue != null)
			sb.append(this.returnValue.String());
		sb.append(";");

		return sb.toString();
	}
}
