package com.lcberg.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.lcberg.ast.Ast;
import com.lcberg.ast.ExpressionStatement;
import com.lcberg.ast.Identifier;
import com.lcberg.ast.IntegerLiteral;
import com.lcberg.ast.LetStatement;
import com.lcberg.ast.ReturnStatement;
import com.lcberg.ast.Statement;
import com.lcberg.lexer.Lexer;

public class ParserTest {
	@Test
	public void TestLetStatements() {
		String input = """
						let x = 5;
						let y = 10;
						let foobar = 838383;
				""";

		Lexer lexer = new Lexer(input);
		Parser parser = new Parser(lexer);

		Ast ast = parser.ParseProgram();

		checkParserErrors(parser);

		if (ast == null) {
			fail("ParseProgram() returned null");
		}
		assertEquals(ast.statements.size(), 3,
				"ast.statements does not contain 3 statements. got " + ast.statements.size());

		String[] tests = new String[] { "x", "y", "foobar" };

		for (int i = 0; i < tests.length; i++) {
			Statement statement = ast.statements.get(i);
			assertTrue(testLetStatement(statement, tests[i]));
		}
	}

	public boolean testLetStatement(Statement statement, String name) {
		if (!statement.TokenLiteral().equals("let")) {
			System.out.println("TokenLiteral not let. got " + statement.TokenLiteral() + ".");
			return false;
		}

		if (!(statement instanceof LetStatement)) {
			System.out.println("Statement not LetStatement. Got " + name + statement.getClass().getName());
			return false;
		}
		LetStatement letStatement = (LetStatement) statement;

		if (!letStatement.name.value.equals(name)) {
			System.out.println("Statement name not " + name + ". Got " + letStatement.name.value);
			return false;
		}

		if (!letStatement.name.TokenLiteral().equals(name)) {
			System.out.println(
					"letStatement.name.TokenLiteral() not " + name + ". Got " + letStatement.name.TokenLiteral());
			return false;
		}

		return true;
	}

	@Test
	public void testReturnStatements() {
		String input = """
					return 5;
					return 10;
					return 993322;
				""";

		Lexer lexer = new Lexer(input);
		Parser parser = new Parser(lexer);

		Ast ast = parser.ParseProgram();

		checkParserErrors(parser);

		if (ast == null) {
			fail("ParseProgram() returned null");
		}
		assertEquals(ast.statements.size(), 3,
				"ast.statements does not contain 3 statements. got " + ast.statements.size());

		for (Statement statement : ast.statements) {
			assertTrue(statement instanceof ReturnStatement, "Statement not Returnstatement, got " + statement);
			assertEquals(statement.TokenLiteral(), "return",
					"ReturnStatement TokenLiteral not return, got " + statement.TokenLiteral());
		}
	}

	public void checkParserErrors(Parser parser) {
		if (parser.errors.size() == 0)
			return;

		System.err.printf("Parser has %d errors", parser.errors.size());
		for (String error : parser.errors) {
			System.err.printf("Parser error: %s \n", error);
		}
		fail("Parser had errors");
	}

	@Test
	public void TestIdentifierExpression() {
		String input = "foobar;";

		Lexer lexer = new Lexer(input);
		Parser parser = new Parser(lexer);
		Ast ast = parser.ParseProgram();
		checkParserErrors(parser);

		assertEquals(1, ast.statements.size());
		assertTrue(ast.statements.get(0) instanceof ExpressionStatement);
		ExpressionStatement expressionStatement = (ExpressionStatement) ast.statements.get(0);
		assertTrue(expressionStatement.expression instanceof Identifier);
		Identifier identifier = (Identifier) expressionStatement.expression;
		assertEquals("foobar", identifier.value);
		assertEquals("foobar", identifier.TokenLiteral());
	}

	@Test
	public void testIntegerLiteralExpression() {
		String input = "5;";

		Lexer lexer = new Lexer(input);
		Parser parser = new Parser(lexer);
		Ast ast = parser.ParseProgram();
		checkParserErrors(parser);

		assertEquals(ast.statements.size(), 1);
		assertTrue(ast.statements.get(0) instanceof ExpressionStatement);
		ExpressionStatement expressionStatement = (ExpressionStatement) ast.statements.get(0);
		assertTrue(expressionStatement.expression instanceof IntegerLiteral);
		IntegerLiteral integerLiteral = (IntegerLiteral) expressionStatement.expression;
		assertEquals(integerLiteral.value, 5);
		assertEquals(integerLiteral.TokenLiteral(), "5");
	}
}
