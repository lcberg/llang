package com.lcberg;

import com.lcberg.repl.Repl;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello, welcome to llang.");
		System.out.println("Feel free to type in commands.");

		Repl repl = new Repl();
		repl.Start();
	}
}
