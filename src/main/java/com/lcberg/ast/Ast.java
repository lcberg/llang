package com.lcberg.ast;

import java.util.ArrayList;

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
