package com.lcberg.ast;

import com.lcberg.token.Token;

public class LetStatement implements Statement {
	public Token token;
	public Identifier name;
	public Expression value;

	public LetStatement(Token token) {
		this.token = token;
	}

	public String statementNode() {
		return "";
	}

	public String TokenLiteral() {
		return token.Literal;
	}
}
