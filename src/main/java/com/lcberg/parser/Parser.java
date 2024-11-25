package com.lcberg.parser;

import java.util.ArrayList;

import com.lcberg.ast.Ast;
import com.lcberg.ast.Identifier;
import com.lcberg.ast.LetStatement;
import com.lcberg.ast.Statement;
import com.lcberg.lexer.Lexer;
import com.lcberg.token.Token;
import com.lcberg.token.TokenType;

public class Parser {
	public Lexer lexer;

	private Token currentToken;
	private Token peekToken;

	private boolean debug = true;

	public Parser(Lexer lexer) {
		this.lexer = lexer;

		NextToken();
		NextToken();
	}

	public void NextToken() {
		this.currentToken = peekToken;
		this.peekToken = lexer.NextToken();

		if (debug) {
			System.out.println("CurrentToken " + currentToken + ", peekToken: " + peekToken);
		}
	}

	public Ast ParseProgram() {
		Ast ast = new Ast();
		ast.statements = new ArrayList<Statement>();

		while (!currentTokenIs(TokenType.EOF)) {
			Statement statement = parseStatement();
			if (statement != null) {
				ast.statements.add(statement);
			}
			NextToken();
		}
		return ast;
	}

	public Statement parseStatement() {
		switch (this.currentToken.Type) {
			case LET:
				return parseLetStatement();
			default:
				return null;
		}
	}

	public LetStatement parseLetStatement() {
		LetStatement letStatement = new LetStatement(this.currentToken);

		if (!expectPeek(TokenType.IDENT)) {
			return null;
		}

		letStatement.name = new Identifier(this.currentToken, this.currentToken.Literal);

		if (!expectPeek(TokenType.ASSIGN)) {
			return null;
		}

		// TODO: Were skipping the expressions until we encounter a semicolon;
		while (!currentTokenIs(TokenType.SEMICOLON)) {
			NextToken();
		}

		return letStatement;
	}

	public boolean currentTokenIs(TokenType type) {
		return currentToken.Type == type;
	}

	public boolean peekTokenIs(TokenType type) {
		return peekToken.Type == type;
	}

	public boolean expectPeek(TokenType type) {
		if (peekTokenIs(type)) {
			NextToken();
			return true;
		} else {
			return false;
		}
	}

}
