package com.upreader;

/**
 * Cookie added to a HTTP Response
 * 
 * @author Flavius
 *
 */
public class ResponseCookie {
	private final String name;
	private final String value;
	private boolean secure = false;
	private int age = 2592000;
	private String path = "";

	public ResponseCookie(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public ResponseCookie setSecure(boolean secure) {
		this.secure = secure;
		return this;
	}

	public ResponseCookie setAge(int age) {
		this.age = age;
		return this;
	}

	public ResponseCookie setPath(String path) {
		this.path = path;
		return this;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public boolean isSecure() {
		return this.secure;
	}

	public int getAge() {
		return this.age;
	}

	public String getPath() {
		return this.path;
	}
}
