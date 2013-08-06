package com.upreader.context;

import com.caucho.server.webapp.WebApp;
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

	/**
	 * Get an UpreaderCookie with the specified name from the request
	 * @param name
	 * @return
	 */
	public UpreaderCookie get(String name) {
		return this.context.request().getCookie(name);
	}
	
	/**
	 * Gets the value of a request cookie
	 * 
	 * @param name
	 * @return
	 */
	public String getValue(String name) {
		UpreaderCookie returnedCookie = get(name);
		return returnedCookie != null ? returnedCookie.getValue() : null;
	}
	
	/**
	 * Check that the cookie contains the specified value
	 *  
	 * @param name cookie name
	 * @param value
	 * @return
	 */
	public boolean test(String name, String value) {
		return StringHelper.equals(value, getValue(name));
	}

	/**
	 * Removes a cookie
	 *
	 * @param name cookie name
	 * @return
	 */
	public Cookies remove(String name) {
		remove(name, "/", WebApp.getCurrent().generateCookieDomain(context.request().getRawRequest()));
		return this;
	}

	/**
	 * Removes a cookie from context
	 * 
	 * @param name cookie name
	 * @param path cookie path
	 * @return
	 */
	public Cookies remove(String name, String path, String domain) {
		this.context.request().deleteCookie(name, path, domain);
		return this;
	}
	
	
	/**
	 * Add a cookie to the response
	 * 
	 * @param responseCookie the cookie to add
	 * @return this
	 */
	public Cookies put(ResponseCookie responseCookie) {
		this.context.request().setCookie(responseCookie.getName(), responseCookie.getValue(), responseCookie.getPath(),
				responseCookie.getAge(), responseCookie.isSecure());

		return this;
	}
}
