package com.lcberg.repl;

import java.util.Scanner;

import com.lcberg.lexer.Lexer;
import com.lcberg.token.Token;
import com.lcberg.token.TokenType;

public class Repl {
	public boolean running = false;
	public final String PROMPT = ">>";

	public void Start() {
		Scanner scanner = new Scanner(System.in);

		running = true;
		while (running) {
			String input = scanner.nextLine();
			if (input == null)
				continue;

			Lexer lexer = new Lexer(input);

			Token token = lexer.NextToken();
			while (token.Type != TokenType.EOF) {
				System.out.printf("%s%n", token);
				token = lexer.NextToken();
			}
		}
		scanner.close();
	}
}
