package com.upreader;

import java.util.Enumeration;

/**
 * Standard HttpSession wrapper for additional functionality
 * 
 * @author Flavius
 *
 */
public class UpreaderSession {
	private final javax.servlet.http.HttpSession session;

	private UpreaderSession(javax.servlet.http.HttpSession session) {
		this.session = session;
	}

	public static UpreaderSession createSession(javax.servlet.http.HttpSession session) {
		if (session == null) {
			return null;
		}

		return new UpreaderSession(session);
	}

	public boolean isNew() {
		return this.session.isNew();
	}

	public Enumeration<String> getAttributeNames() {
		return this.session.getAttributeNames();
	}

	public Object getAttribute(String name) {
		return this.session.getAttribute(name);
	}

	public int getMaxInactiveInterval() {
		return this.session.getMaxInactiveInterval();
	}

	public void setAttribute(String name, Object o) {
		this.session.setAttribute(name, o);
	}

	public void removeAttribute(String name) {
		this.session.removeAttribute(name);
	}

	public String getId() {
		return this.session.getId();
	}

	public void invalidate() {
		this.session.invalidate();
	}

	public void setMaxInactiveInterval(int timeout) {
		this.session.setMaxInactiveInterval(timeout);
	}

	public int hashCode() {
		return this.session.hashCode();
	}
}
