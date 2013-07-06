package com.upreader.context;

import com.upreader.UpreaderRequest;

/**
 * Handles HTTP headers for an UpreaderRequest
 * 
 * @author Flavius
 *
 */
public class Headers {
	private final UpreaderRequest request;

	public Headers(Context context) {
		this.request = context.request();
	}

	public Headers put(String headerName, String value) {
		this.request.setResponseHeader(headerName, value);

		return this;
	}

	public String get(String name) {
		return this.request.getHeader(name);
	}

	public String referrer() {
		return get("Referer");
	}

	public String userAgent() {
		return get("User-Agent");
	}

	public String accept() {
		return get("Accept");
	}

	public String host() {
		return get("Host");
	}

	public Headers expires(int secondsFromNow) {
		this.request.setExpiration(secondsFromNow);
		return this;
	}
}
