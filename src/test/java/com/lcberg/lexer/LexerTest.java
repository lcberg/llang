package com.lcberg.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.lcberg.token.*;

public class LexerTest {
	record TestCase(TokenType expectedType, String expectedLiteral) {
	}

	@Test
	public void TestNextToken() {
		String input = "=+(){},;";

		List<TestCase> testCases = List.of(
				new TestCase(TokenType.ASSIGN, "="),
				new TestCase(TokenType.PLUS, "+"),
				new TestCase(TokenType.LPAREN, "("),
				new TestCase(TokenType.RPAREN, ")"),
				new TestCase(TokenType.LBRACE, "{"),
				new TestCase(TokenType.RBRACE, "}"),
				new TestCase(TokenType.COMMA, ","),
				new TestCase(TokenType.SEMICOLON, ";"));

		Lexer lexer = new Lexer(input);

		for (TestCase testCase : testCases) {
			Token token = lexer.NextToken();

			assertEquals(token.Literal, testCase.expectedLiteral());
			assertEquals(token.Type, testCase.expectedType);
		}
	}

	@Test
	public void TestNextToken2() {
		String input = """
				let five = 5;
				let ten = 10;

				let add = fn(x, y) {
					x + y;
				};

				let result = add(five, ten);
				""";

		List<TestCase> testCases = List.of(
				new TestCase(TokenType.LET, "let"),
				new TestCase(TokenType.IDENT, "five"),
				new TestCase(TokenType.ASSIGN, "="),
				new TestCase(TokenType.INT, "5"),
				new TestCase(TokenType.SEMICOLON, ";"),
				new TestCase(TokenType.LET, "let"),
				new TestCase(TokenType.IDENT, "ten"),
				new TestCase(TokenType.ASSIGN, "="),
				new TestCase(TokenType.INT, "10"),
				new TestCase(TokenType.SEMICOLON, ";"),
				new TestCase(TokenType.LET, "let"),
				new TestCase(TokenType.IDENT, "add"),
				new TestCase(TokenType.ASSIGN, "="),
				new TestCase(TokenType.FUNCTION, "fn"),
				new TestCase(TokenType.LPAREN, "("),
				new TestCase(TokenType.IDENT, "x"),
				new TestCase(TokenType.COMMA, ","),
				new TestCase(TokenType.IDENT, "y"),
				new TestCase(TokenType.RPAREN, ")"),
				new TestCase(TokenType.LBRACE, "{"),
				new TestCase(TokenType.IDENT, "x"),
				new TestCase(TokenType.PLUS, "+"),
				new TestCase(TokenType.IDENT, "y"),
				new TestCase(TokenType.SEMICOLON, ";"),
				new TestCase(TokenType.RBRACE, "}"),
				new TestCase(TokenType.SEMICOLON, ";"),
				new TestCase(TokenType.LET, "let"),
				new TestCase(TokenType.IDENT, "result"),
				new TestCase(TokenType.ASSIGN, "="),
				new TestCase(TokenType.IDENT, "add"),
				new TestCase(TokenType.LPAREN, "("),
				new TestCase(TokenType.IDENT, "five"),
				new TestCase(TokenType.COMMA, ","),
				new TestCase(TokenType.IDENT, "ten"),
				new TestCase(TokenType.RPAREN, ")"),
				new TestCase(TokenType.SEMICOLON, ";"),
				new TestCase(TokenType.EOF, ""));

		Lexer lexer = new Lexer(input);
		System.out.println(input);

		int counter = 0;
		for (TestCase testCase : testCases) {
			System.out.println("Case :" + counter);
			Token token = lexer.NextToken();

			System.out.println(token.Literal + token.Type.toString());
			System.out.println(lexer.position);
			System.out.println("CH: " + lexer.ch);

			assertEquals(token.Literal, testCase.expectedLiteral());
			assertEquals(token.Type, testCase.expectedType);

			counter++;
		}
	}
}