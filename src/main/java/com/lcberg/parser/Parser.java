package com.lcberg.parser;

import com.lcberg.ast.Ast;
import com.lcberg.lexer.Lexer;
import com.lcberg.token.Token;

public class Parser {
	public Lexer lexer;

	private Token currentToken;
	private Token peekToken;

	public Parser(Lexer lexer) {
		this.lexer = lexer;

		NextToken();
		NextToken();
	}

	public void NextToken() {
		this.currentToken = peekToken;
		this.peekToken = lexer.NextToken();
	}

	public Ast ParseProgram() {
		return null;
	}
}
