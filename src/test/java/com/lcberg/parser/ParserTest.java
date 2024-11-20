package com.lcberg.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.lcberg.ast.Ast;
import com.lcberg.ast.LetStatement;
import com.lcberg.ast.Statement;
import com.lcberg.lexer.Lexer;

public class ParserTest {
	@Test
	public void TestLetStatement() {
		String input = """
						let x = 5;
						let y = 10;
						let foobar = 838383;
				""";

		Lexer lexer = new Lexer(input);
		Parser parser = new Parser(lexer);

		Ast ast = parser.ParseProgram();

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
}
