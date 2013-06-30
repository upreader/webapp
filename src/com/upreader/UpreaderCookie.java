package com.upreader;

/**
 * Standard cookie wrapper for additional functionality
 * 
 * @author Flavius
 *
 */
public class UpreaderCookie {
	private javax.servlet.http.Cookie cookie;

	public UpreaderCookie(javax.servlet.http.Cookie cookie) {
		this.cookie = cookie;
	}

	public String getValue() {
		return this.cookie.getValue();
	}

	public String getName() {
		return this.cookie.getName();
	}
}
