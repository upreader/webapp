package com.upreader.js;

public interface Visitor {
	public boolean hasNext();
	public boolean isArray();
	public String name();
	public void next();
	public Object value();
}
