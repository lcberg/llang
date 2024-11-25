package com.lcberg.parser;

import com.lcberg.ast.Expression;

@FunctionalInterface
interface PrefixParseFn {
	Expression parse();
}

@FunctionalInterface
interface InfixParseFn {
	Expression parse(Expression left);
}
