package com.lcberg.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.lcberg.ast.Ast;
import com.lcberg.ast.BooleanLiteral;
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

		System.out.printf("Parser has %d errors", parser.errors.size());
		for (String error : parser.errors) {
			System.out.printf("Parser error: %s \n", error);
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

	@Test
	public void testParsingPrefixExpression() {
		record TestCase(String input, String operator, int integerValue) {
		}
		;
		List<TestCase> testCases = List.of(
				new TestCase("!5;", "!", 5),
				new TestCase("-15;", "-", 15));

		for (TestCase testCase : testCases) {
			Lexer lexer = new Lexer(testCase.input());
			Parser parser = new Parser(lexer);
			Ast ast = parser.ParseProgram();
			checkParserErrors(parser);

			assertEquals(ast.statements.size(), 1);
			assertTrue(ast.statements.get(0) instanceof ExpressionStatement);
			ExpressionStatement expressionStatement = (ExpressionStatement) ast.statements.get(0);
			assertTrue(expressionStatement.expression instanceof PrefixExpression);
			PrefixExpression prefixExpression = (PrefixExpression) expressionStatement.expression;
			assertEquals(prefixExpression.operator, testCase.operator());
			testIntegerLiteral(prefixExpression.right, testCase.integerValue());
		}
	}

	public void testIntegerLiteral(Expression expression, int value) {
		assertTrue(expression instanceof IntegerLiteral);
		IntegerLiteral integerLiteral = (IntegerLiteral) expression;
		assertEquals(integerLiteral.value, value);
		assertEquals(integerLiteral.TokenLiteral(), value + "");
	}

	@Test
	public void testParsingInfixExpressions() {
		record TestCase(String input, int leftValue, String operator, int rightValue) {
		}
		List<TestCase> testCases = List.of(
				new TestCase("5 + 5;", 5, "+", 5),
				new TestCase("5 - 5;", 5, "-", 5),
				new TestCase("5 * 5;", 5, "*", 5),
				new TestCase("5 / 5;", 5, "/", 5),
				new TestCase("5 > 5;", 5, ">", 5),
				new TestCase("5 < 5;", 5, "<", 5),
				new TestCase("5 == 5;", 5, "==", 5),
				new TestCase("5 != 5;", 5, "!=", 5));

		for (TestCase testCase : testCases) {
			Lexer lexer = new Lexer(testCase.input());
			Parser parser = new Parser(lexer);
			Ast ast = parser.ParseProgram();
			checkParserErrors(parser);

			assertEquals(ast.statements.size(), 1);

			assertTrue(ast.statements.get(0) instanceof ExpressionStatement);
			ExpressionStatement expressionStatement = (ExpressionStatement) ast.statements.get(0);
			assertTrue(expressionStatement.expression instanceof InfixExpression);
			InfixExpression infixExpression = (InfixExpression) expressionStatement.expression;
			testIntegerLiteral(infixExpression.left, testCase.leftValue());
			assertEquals(testCase.operator, infixExpression.operator);
			testIntegerLiteral(infixExpression.right, testCase.rightValue());
		}
	}

	@Test
	public void testOperatorPrecedenceParsing() {
		record TestCase(String input, String expected) {
		}
		List<TestCase> testCases = List.of(
				new TestCase("-a * b", "((-a) * b)"),
				new TestCase("!-a", "(!(-a))"),
				new TestCase("a + b + c", "((a + b) + c)"),
				new TestCase("a + b - c", "((a + b) - c)"),
				new TestCase("a * b * c", "((a * b) * c)"),
				new TestCase("a * b / c", "((a * b) / c)"),
				new TestCase("a + b / c", "(a + (b / c))"),
				new TestCase("a + b * c + d / e - f", "(((a + (b * c)) + (d / e)) - f)"),
				new TestCase("3 + 4; -5 * 5", "(3 + 4)((-5) * 5)"),
				new TestCase("5 > 4 == 3 < 4", "((5 > 4) == (3 < 4))"),
				new TestCase("3 + 4 * 5 == 3 * 1 + 4 * 5", "((3 + (4 * 5)) == ((3 * 1) + (4 * 5)))"));

		for (TestCase testCase : testCases) {
			Lexer lexer = new Lexer(testCase.input());
			Parser parser = new Parser(lexer);
			Ast ast = parser.ParseProgram();
			checkParserErrors(parser);

			assertEquals(ast.String(), testCase.expected());
		}
	}

	@Test
	public void testIdentifier(Expression expression, String value) {
		assertTrue(expression instanceof Identifier);
		Identifier identifier = (Identifier) expression;

		assertEquals(identifier.value, value);
		assertEquals(identifier.TokenLiteral(), value);
	}

	@Test
	public void testLiteralExpression(
			Expression expression, Object expected) {
		if (expected instanceof Integer) {
			testIntegerLiteral(expression, ((Integer) expected));
		} else if (expected instanceof Long) {
			testIntegerLiteral(expression, (Integer) expected);
		} else if (expected instanceof String) {
			testIdentifier(expression, (String) expected);
		}
		fail("Type of exp not handled. Got " + expected.getClass().getSimpleName());
	}

	@Test
	public void testInfixExpression(Expression expression, Object left, String operator, Object right) {
		assertTrue(expression instanceof InfixExpression);
		InfixExpression infixExpression = (InfixExpression) expression;

		testLiteralExpression(infixExpression.left, left);

		assertEquals(infixExpression.operator, operator);
		testLiteralExpression(infixExpression.right, right);
	}

	@Test
	public void testBooleanExpression() {
		record TestCase(String input, Boolean expectedValue) {
		}
		List<TestCase> testCases = List.of(
				new TestCase("true;", true),
				new TestCase("false;", false));

		for (TestCase testCase : testCases) {
			Lexer lexer = new Lexer(testCase.input);
			Parser parser = new Parser(lexer);
			Ast ast = parser.ParseProgram();
			checkParserErrors(parser);

			assertEquals(ast.statements.size(), 1);
			assertTrue(ast.statements.get(0) instanceof ExpressionStatement);
			ExpressionStatement expressionStatement = (ExpressionStatement) ast.statements.get(0);
			assertTrue(expressionStatement.expression instanceof BooleanLiteral);
			BooleanLiteral boolean1 = (BooleanLiteral) expressionStatement.expression;
			assertEquals(boolean1.value, testCase.expectedValue);
		}
	}
}
