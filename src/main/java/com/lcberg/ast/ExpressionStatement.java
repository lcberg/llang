package com.lcberg.ast;

import com.lcberg.token.Token;

public class ExpressionStatement implements Statement {
	public Token token;
	public Expression expression;

	public ExpressionStatement(Token token) {
		this.token = token;
	}

	public String statementNode() {
		return "";
	}

	public String TokenLiteral() {
		return this.token.Literal;
	}

	public String String() {
		if (this.expression != null)
			return this.expression.String();

		return "";
	}

}
