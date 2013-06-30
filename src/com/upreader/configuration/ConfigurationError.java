package com.upreader.configuration;

public class ConfigurationError extends Error {
	private static final long serialVersionUID = -5178535000335597142L;

	public ConfigurationError(String message) {
		super(message);
	}

	public ConfigurationError(String message, Throwable cause) {
		super(message, cause);
	}
}
