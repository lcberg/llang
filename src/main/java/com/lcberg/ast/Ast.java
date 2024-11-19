package com.lcberg.ast;

import java.util.ArrayList;

import com.lcberg.token.Token;

interface Node {
	public String TokenLiteral();
}

interface Statement extends Node {
	public String statementNode();
}

interface Expression extends Node {
	public String expressionNode();
}

public class Ast {
	public ArrayList<Statement> statements;

	public Ast() {
		statements = new ArrayList<Statement>();
	}

	public String TokenLiteral() {
		if (statements.size() > 0)
			return statements.getFirst().TokenLiteral();
		return "";
	}
}

class Identifier implements Expression {
	Token token;
	String value;

	public String expressionNode() {
		return "";
	}

	public String TokenLiteral() {
		return token.Literal;
	}
}

class LetStatement implements Statement {
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
