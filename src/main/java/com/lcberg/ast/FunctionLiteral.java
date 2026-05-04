package com.lcberg.ast;

import java.util.ArrayList;

import com.lcberg.token.Token;

public class FunctionLiteral implements Expression {
	public Token token;
	public ArrayList<Identifier> parameters;
	public BlockStatement body;

	public FunctionLiteral(Token token) {
		this.token = token;
		this.parameters = new ArrayList<Identifier>();
	}

	public String TokenLiteral() {
		return "";
	}

	public String expressionNode() {
		return "";
	}

	public String String() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.TokenLiteral());
		sb.append("(");

		for (int i = 0; i < this.parameters.size(); i++) {
			sb.append(this.parameters.get(i).String());
			if (i < this.parameters.size() - 1)
				sb.append(", ");
		}

		return "";
	}
}
