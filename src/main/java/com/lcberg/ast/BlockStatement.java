package com.lcberg.ast;

import java.util.ArrayList;
import com.lcberg.token.Token;

public class BlockStatement implements Expression {
	public Token token;
	public ArrayList<Statement> statements;

	public BlockStatement(Token token) {
		this.token = token;
		this.statements = new ArrayList<Statement>();
	}

	public String TokenLiteral() {
		return this.token.Literal;
	}

	public String String() {
		StringBuilder sb = new StringBuilder();
		for (Statement statement : this.statements) {
			sb.append(statement.String());
		}
		return sb.toString();
	}

	public String expressionNode() {
		return "";
	}

}
