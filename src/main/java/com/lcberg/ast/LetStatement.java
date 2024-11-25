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

	public String String() {
		StringBuilder sb = new StringBuilder();

		sb.append(TokenLiteral());
		sb.append(" ");
		sb.append(this.name.String());
		sb.append(" = ");

		if (this.value != null) {
			sb.append(this.value.String());
		}
		sb.append(";");

		return sb.toString();
	}
}
