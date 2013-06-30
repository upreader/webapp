package com.upreader.context;

import com.upreader.ResponseCookie;
import com.upreader.UpreaderCookie;
import com.upreader.helper.StringHelper;

/**
 * Handles request and response cookies in a Context
 * 
 * @author Flavius
 *
 */
public class Cookies {
	private final Context context;

	public Cookies(Context context) {
		this.context = context;
	}

	public UpreaderCookie get(String name) {
		return this.context.getRequest().getCookie(name);
	}

	public String getValue(String name) {
		UpreaderCookie returnedCookie = get(name);
		return returnedCookie != null ? returnedCookie.getValue() : null;
	}

	public boolean test(String name, String value) {
		return StringHelper.equals(value, getValue(name));
	}

	public Cookies remove(String name) {
		remove(name, this.context.getInfrastructure().getUrl());

		return this;
	}

	public Cookies remove(String name, String path) {
		this.context.getRequest().deleteCookie(name, path);

		return this;
	}

	public Cookies put(ResponseCookie responseCookie) {
		this.context.getRequest().setCookie(responseCookie.getName(), responseCookie.getValue(), responseCookie.getPath(),
				responseCookie.getAge(), responseCookie.isSecure());

		return this;
	}
}
