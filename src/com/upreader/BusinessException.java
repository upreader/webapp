package com.upreader;

/**
 * Application errors that will be shown to the user
 * 
 * @author Flavius
 *
 */
public class BusinessException extends Exception {
	public BusinessException(String message) {
		super(message);
	}
}
