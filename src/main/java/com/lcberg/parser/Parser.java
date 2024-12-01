package com.lcberg.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.lcberg.ast.Ast;
import com.lcberg.ast.Expression;
import com.lcberg.ast.ExpressionStatement;
import com.lcberg.ast.Identifier;
import com.lcberg.ast.InfixExpression;
import com.lcberg.ast.IntegerLiteral;
import com.lcberg.ast.LetStatement;
import com.lcberg.ast.PrefixExpression;
import com.lcberg.ast.ReturnStatement;
import com.lcberg.ast.Statement;
import com.lcberg.lexer.Lexer;
import com.lcberg.token.Token;
import com.lcberg.token.TokenType;

public class Parser {
	public Lexer lexer;

	private Token currentToken;
	private Token peekToken;
	private boolean debug = true;
	public ArrayList<String> errors;

	private Map<TokenType, PrefixParseFn> prefixParseFns = new HashMap<TokenType, PrefixParseFn>();
	private Map<TokenType, InfixParseFn> infixParseFns = new HashMap<TokenType, InfixParseFn>();
	private Map<TokenType, Precedence> precedenceMap = new HashMap<TokenType, Precedence>();

	private void registerTokenPrecedence() {
		precedenceMap.put(TokenType.EQUALS, Precedence.EQUALS);
		precedenceMap.put(TokenType.NOT_EQUALS, Precedence.EQUALS);
		precedenceMap.put(TokenType.LESS_THAN, Precedence.LESSGREATER);
		precedenceMap.put(TokenType.GREATER_THAN, Precedence.LESSGREATER);
		precedenceMap.put(TokenType.PLUS, Precedence.SUM);
		precedenceMap.put(TokenType.MINUS, Precedence.SUM);
		precedenceMap.put(TokenType.SLASH, Precedence.PRODUCT);
		precedenceMap.put(TokenType.ASTERISK, Precedence.PRODUCT);
	}

	public Parser(Lexer lexer) {
		this.lexer = lexer;
		this.errors = new ArrayList<String>();

		this.registerPrefix(TokenType.IDENT, this::parseIdentifier);
		this.registerPrefix(TokenType.INT, this::parseIntegerLiteral);
		this.registerPrefix(TokenType.BANG, this::parsePrefixExpression);
		this.registerPrefix(TokenType.MINUS, this::parsePrefixExpression);

		this.registerInfix(TokenType.PLUS, this::parseInfixExpression);
		this.registerInfix(TokenType.MINUS, this::parseInfixExpression);
		this.registerInfix(TokenType.SLASH, this::parseInfixExpression);
		this.registerInfix(TokenType.ASTERISK, this::parseInfixExpression);
		this.registerInfix(TokenType.EQUALS, this::parseInfixExpression);
		this.registerInfix(TokenType.NOT_EQUALS, this::parseInfixExpression);
		this.registerInfix(TokenType.LESS_THAN, this::parseInfixExpression);
		this.registerInfix(TokenType.GREATER_THAN, this::parseInfixExpression);

		this.registerTokenPrecedence();

		NextToken();
		NextToken();
	}

	// Takes in the left side of an expression (everything is an expression even a
	// 5)
	// and creates an infix expression with left, right and operator
	// this advances the token to the end of the whole expression (since an infix
	// expression has 3 expression tokens)
	public InfixExpression parseInfixExpression(Expression left) {
		InfixExpression infixExpression = new InfixExpression(this.currentToken, this.currentToken.Literal, left);

		Precedence precedence = this.currentPrecedence();
		this.NextToken();
		infixExpression.right = this.parseExpression(precedence);

		return infixExpression;
	}

	// handles both minus and ! as prefixes
	// also handles non existing prefixes Expressions without a leading operator
	// (like identifiers or integers)
	// Advances Token to
	public PrefixExpression parsePrefixExpression() {
		PrefixExpression prefixExpression = new PrefixExpression(this.currentToken, this.currentToken.Literal);

		this.NextToken();

		prefixExpression.right = parseExpression(Precedence.PREFIX);
		return prefixExpression;
	}

	public void noPrefixParseFnError(TokenType tokenType) {
		String message = String.format("No prefix parse function for %s found", tokenType);
		this.errors.add(message);
	}

	public Precedence peekPrecedence() {
		if (this.precedenceMap.containsKey(this.peekToken.Type)) {
			Precedence precedence = this.precedenceMap.get(this.peekToken.Type);
			return precedence;
		}
		return Precedence.LOWEST;
	}

	public Precedence currentPrecedence() {
		if (this.precedenceMap.containsKey(this.currentToken.Type)) {
			Precedence precedence = this.precedenceMap.get(this.currentToken.Type);
			return precedence;
		}
		return Precedence.LOWEST;
	}

	// Idenfiers are expressions to - do they have feelings as well?
	public Expression parseIdentifier() {
		return new Identifier(this.currentToken, this.currentToken.Literal);
	}

	public void NextToken() {
		this.currentToken = peekToken;
		this.peekToken = lexer.NextToken();

		if (debug) {
			System.out.println("CurrentToken " + currentToken + ", peekToken: " + peekToken);
		}
	}

	public Statement parseStatement() {
		switch (this.currentToken.Type) {
			case LET:
				return parseLetStatement();
			case RETURN:
				return parseReturnStatement();
			default:
				return parseExpressionStatement();
		}
	}

	public ExpressionStatement parseExpressionStatement() {
		ExpressionStatement statement = new ExpressionStatement(this.currentToken);
		statement.expression = this.parseExpression(Precedence.LOWEST);

		if (this.peekTokenIs(TokenType.SEMICOLON)) // Semicolons are not part of the AST
			this.NextToken();

		return statement;
	}

	// start with parsing the first token as a prefix (or nonfix expression) and if
	// there is more (no smicolon or binding of following token is higher) create an
	// infix expression with it
	public Expression parseExpression(Precedence precedence) {
		if (!this.prefixParseFns.containsKey(this.currentToken.Type)) {
			this.noPrefixParseFnError(this.currentToken.Type);
			return null;
		}
		PrefixParseFn prefix = this.prefixParseFns.get(this.currentToken.Type);
		Expression left = prefix.parse();

		while (!this.peekTokenIs(TokenType.SEMICOLON) && precedence.getValue() < this.peekPrecedence().getValue()) {
			if (this.infixParseFns.containsKey(this.peekToken.Type)) {
				InfixParseFn infix = this.infixParseFns.get(this.peekToken.Type);
				this.NextToken();
				left = infix.parse(left); // current expression will be left side of the infix expression, if next token
											// does not have precedence
			} else
				return left; // will return the current expression if at "end" of infix Expression
		}

		return left;
	}

	public ReturnStatement parseReturnStatement() {
		ReturnStatement returnStatement = new ReturnStatement(this.currentToken);
		NextToken();

		// TODO: were skipping the expression until we encounter a semicolon
		while (!currentTokenIs(TokenType.SEMICOLON)) {
			NextToken();
		}
		return returnStatement;
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

		// TODO: Were skipping the expressions until we encounter a semicolon
		while (!currentTokenIs(TokenType.SEMICOLON)) {
			NextToken();
		}

		return letStatement;
	}

	public IntegerLiteral parseIntegerLiteral() {
		try {
			int value = Integer.parseInt(this.currentToken.Literal);
			return new IntegerLiteral(value, this.currentToken);
		} catch (NumberFormatException e) {
			String message = String.format("Tried parsing an integer which was not parsable. Got %s\n",
					this.currentToken.Literal);
			this.errors.add(message);
			return null;
		}
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
			peekError(type);
			return false;
		}
	}

	public void peekError(TokenType tokenType) {
		String message = String.format("Expected next token to be %s, got %s instead.", tokenType, this.peekToken.Type);
		this.errors.add(message);
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

	public void registerPrefix(TokenType tokenType, PrefixParseFn fn) {
		this.prefixParseFns.put(tokenType, fn);
	}

	public void registerInfix(TokenType tokenType, InfixParseFn fn) {
		this.infixParseFns.put(tokenType, fn);
	}
}
