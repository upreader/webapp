package com.upreader.js;

public interface VisitorFactory<T> {
	public Visitor visitor(T paramT);
}
