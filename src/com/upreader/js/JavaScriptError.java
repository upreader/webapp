package com.upreader.js;

public class JavaScriptError extends Error {
	private static final long serialVersionUID = 4617820743753617720L;

	public JavaScriptError(String message) {
		super(message);
	}

	public JavaScriptError(String message, Throwable cause) {
		super(message, cause);
	}
}
