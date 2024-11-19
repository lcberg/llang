package com.lcberg.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.lcberg.ast.Ast;
import com.lcberg.lexer.Lexer;

public class ParserTest {
	@Test
	public void TestLetStatement() {
		String input = """
						let x = 5;
						let y = 10;
						let foobar = 838383
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
	}
}
