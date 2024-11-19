package com.lcberg.ast;

import com.lcberg.token.Token;

public class LetStatement implements Statement {
	Token token;
	Identifier identifier;
	Expression value;

	public String statementNode() {
		return "";
	}

	public String TokenLiteral() {
		return token.Literal;
	}
}
