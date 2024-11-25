package com.lcberg.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.lcberg.token.Token;
import com.lcberg.token.TokenType;

public class AstTest {
	@Test
	public void TestString() {
		Ast ast = new Ast();
		Token letStatementToken = new Token(TokenType.LET, "let");
		LetStatement letStatement = new LetStatement(letStatementToken);
		Token myVarToken = new Token(TokenType.IDENT, "myVar");
		letStatement.name = new Identifier(myVarToken, "myVar");
		Token anotherVarToken = new Token(TokenType.IDENT, "anotherVar");
		letStatement.value = new Identifier(anotherVarToken, "anotherVar");
		ast.statements.add(letStatement);

		assertEquals("let myVar = anotherVar;", ast.String());
	}
}
