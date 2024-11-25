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
}
