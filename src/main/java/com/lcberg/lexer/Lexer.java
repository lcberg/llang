package com.lcberg.lexer;

import com.lcberg.token.*;

public class Lexer {
	public String input;
	public Integer position = 0;
	public Integer readPosition = 0;

	public char ch;

	public Lexer(String input) {
		this.input = input;
		this.readChar();
	}

	public char readChar() {
		if (this.readPosition >= this.input.length())
			this.ch = 0;
		else
			this.ch = this.input.charAt(this.readPosition);
		this.position = this.readPosition;
		this.readPosition++;
		return this.ch;
	}

	public Token NextToken() {
		Token token;

		this.skipWhitespace();
		switch (this.ch) {
			case '=':
				token = new Token(TokenType.ASSIGN, String.valueOf(this.ch));
				break;
			case ';':
				token = new Token(TokenType.SEMICOLON, String.valueOf(this.ch));
				break;
			case '(':
				token = new Token(TokenType.LPAREN, String.valueOf(this.ch));
				break;
			case ')':
				token = new Token(TokenType.RPAREN, String.valueOf(this.ch));
				break;
			case ',':
				token = new Token(TokenType.COMMA, String.valueOf(this.ch));
				break;
			case '+':
				token = new Token(TokenType.PLUS, String.valueOf(this.ch));
				break;
			case '-':
				token = new Token(TokenType.MINUS, String.valueOf(this.ch));
				break;
			case '{':
				token = new Token(TokenType.LBRACE, String.valueOf(this.ch));
				break;
			case '}':
				token = new Token(TokenType.RBRACE, String.valueOf(this.ch));
				break;
			case 0:
				token = new Token(TokenType.EOF, "");
				break;
			default:
				if (isLetter(ch)) {
					String literal = readIdentifier();
					TokenType tokenType = Token.lookupIdentifier(literal);
					token = new Token(tokenType, literal);
					return token;
				} else if (isDigit(ch)) {
					String literal = readNumber();
					token = new Token(TokenType.INT, literal);
					return token;
				} else {
					token = new Token(TokenType.ILLEGAL, "");
					break;
				}
		}

		this.readChar();

		return token;
	}

	// TODO: Support more than integers
	public String readNumber() {
		int startPosition = this.position;
		while (isDigit(this.ch)) {
			readChar();
		}
		return this.input.substring(startPosition, this.position);
	}

	public boolean isDigit(char ch) {
		return '0' <= ch && ch <= '9';
	}

	// no numbers in identifiers allowed
	private boolean isLetter(char ch) {
		return 'a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z' || ch == '_';
	}

	public String readIdentifier() {
		int startPosition = this.position;
		while (isLetter(this.ch)) {
			readChar();
		}
		return this.input.substring(startPosition, this.position);
	}

	private void skipWhitespace() {
		while (this.ch == ' ' || this.ch == '\t' || this.ch == '\n' || this.ch == '\r')
			this.readChar();
	}
}
