package com.upreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.upreader.helper.StringHelper;

/**
 * Wraps a single HTTP request and response
 * 
 * @author Flavius
 */
public class UpreaderRequest {
	private Logger log = Logger.getLogger(UpreaderRequest.class);
	private final UpreaderApplication application;
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final ServletContext servletContext;
	private final String method;
	private boolean rewritten = false;
	private Map<String, List<String>> rewrittenParameters = null;
	private boolean jspHasBeenIncluded = false;
	private final Infrastructure infrastructure;

	public UpreaderRequest(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext,
			UpreaderApplication application) {
		this.application = application;
		this.request = request;
		this.response = response;
		this.servletContext = servletContext;
		this.method = request.getMethod();
		this.infrastructure = application.getInfrastructure();

		response.setContentType("text/html;charset=utf-8");
	}

	public UpreaderApplication getApplication() {
		return application;
	}

	public HttpServletRequest getRawRequest() {
		return this.request;
	}

	public ServletContext getServletContext() {
		return this.servletContext;
	}

	public HttpServletResponse getRawResponse() {
		return this.response;
	}

	public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {
		this.response.setCharacterEncoding(encoding);
		this.request.setCharacterEncoding(encoding);
	}

	public Enumeration<String> getHeaderNames() {
		return this.request.getHeaderNames();
	}

	public String getHeader(String name) {
		return this.request.getHeader(name);
	}

	public Enumeration<String> getParameterNames() {
		if (this.rewritten) {
			Set allParams = new HashSet();
			allParams.addAll(this.rewrittenParameters.keySet());
			for (Enumeration name = this.request.getParameterNames(); name.hasMoreElements();) {
				allParams.add((String) name.nextElement());
			}
			return Collections.enumeration(allParams);
		}

		return this.request.getParameterNames();
	}

	public String getParameter(String name) {
		if (this.rewritten) {
			List values = (List) this.rewrittenParameters.get(name);
			if ((values == null) || (values.isEmpty())) {
				return this.request.getParameter(name);
			}
			return (String) values.get(0);
		}

		return this.request.getParameter(name);
	}

	public void putParameter(String name, String value) {
		if (this.rewrittenParameters == null) {
			this.rewrittenParameters = new HashMap();
		}
		if (this.rewrittenParameters.get(name) == null) {
			this.rewrittenParameters.put(name, new ArrayList());
		}
		((List) this.rewrittenParameters.get(name)).add(value);
	}

	public void removeParameter(String name) {
		if (this.rewrittenParameters != null) {
			this.rewrittenParameters.remove(name);
		}
	}

	public String[] getParameterValues(String name) {
		if (this.rewritten) {
			List values = (List) this.rewrittenParameters.get(name);
			if (values == null) {
				return this.request.getParameterValues(name);
			}
			return (String[]) values.toArray(new String[values.size()]);
		}
		return this.request.getParameterValues(name);
	}

	public Map<String, String> getAllRequestParameters() {
		HashMap map = new HashMap();
		Enumeration enumeration = getParameterNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			map.put(name, this.request.getParameter(name));
		}
		return map;
	}

	public void removeAllRequestValues() {
		if (this.rewrittenParameters != null) {
			this.rewrittenParameters.clear();
		}
	}

	public String encodeURL(String url) {
		return this.response.encodeURL(url);
	}

	public void print(String text) throws IOException {
		this.response.getWriter().println(text);
	}

	public PrintWriter getWriter() throws IOException {
		return this.response.getWriter();
	}

	public String getRealPath(String path) {
		return this.servletContext.getRealPath(path);
	}

	public String getContextPath() {
		return this.request.getContextPath();
	}

	public StringBuffer getRequestURL() {
		return this.request.getRequestURL();
	}

	public String getRequestURI() {
		return this.request.getRequestURI();
	}

	public UpreaderCookie getCookie(String name) {
		UpreaderCookie returnedCookie = null;
		javax.servlet.http.Cookie[] theCookies = this.request.getCookies();

		if (theCookies != null) {
			for (int i = 0; i < theCookies.length; i++) {
				if (theCookies[i].getName().equals(name)) {
					returnedCookie = new UpreaderCookie(theCookies[i]);
					break;
				}
			}
		}

		return returnedCookie;
	}

	public void setCookie(String name, String value, String path, int age, boolean secure) {
		javax.servlet.http.Cookie targetCookie = new javax.servlet.http.Cookie(name, value);
		if (StringHelper.isNonEmpty(path)) {
			targetCookie.setPath(path);
		}
		targetCookie.setSecure(secure);

		targetCookie.setMaxAge(age);
		this.response.addCookie(targetCookie);
	}

	public void deleteCookie(String name, String path) {
		javax.servlet.http.Cookie deleteCookie = new javax.servlet.http.Cookie(name, "");
		if (StringHelper.isNonEmpty(path)) {
			deleteCookie.setPath(path);
		}
		deleteCookie.setMaxAge(0);
		this.response.addCookie(deleteCookie);
	}

	public String getClientID() {
		return this.request.getRemoteAddr();
	}

	public String getRequestMethod() {
		return this.request.getMethod();
	}

	public boolean redirect(String redirectDestinationUrl) {
		try {
			this.response.sendRedirect(redirectDestinationUrl);
			return true;
		} catch (IOException ioexc) {
		}
		return false;
	}

	public boolean redirectPermanent(String redirectDestinationUrl) {
		try {
			setResponseHeader("Location", redirectDestinationUrl);
			this.response.sendError(301);
			return true;
		} catch (Exception exc) {
		}
		return false;
	}

	public boolean render(String pageName, boolean fullyQualified) throws Exception {
		return forwardToJsp(pageName, fullyQualified);
	}

	protected boolean forwardToJsp(String pageName, boolean fullyQualified) throws ServletException, IOException {
		String path = "";

		if (fullyQualified) {
			path = pageName;
		} else {
			path = this.infrastructure.getJspDirectory() + pageName;
		}

		RequestDispatcher requestDispatcher = this.servletContext.getRequestDispatcher(path);
		requestDispatcher.include(this.request, this.response);

		this.jspHasBeenIncluded = true;

		return true;
	}

	public void setResponseHeader(String headerName, String value) {
		this.response.setHeader(headerName, value);
	}

	public OutputStream getOutputStream() throws IOException {
		return this.response.getOutputStream();
	}

	public InputStream getInputStream() throws IOException {
		return this.request.getInputStream();
	}

	public void setContentType(String contentType) {
		this.response.setContentType(contentType);
	}

	public void setExpiration(int secondsFromNow) {
		this.response.setDateHeader("Expires", System.currentTimeMillis() + secondsFromNow * 1000L);
	}

	public String getCurrentURI() {
		return this.request.getRequestURI();
	}

	public boolean isSecure() {
		return this.request.isSecure();
	}

	public boolean isCommitted() {
		return this.response.isCommitted();
	}

	public void sendError(int error) throws IOException {
		this.response.sendError(error);
	}

	public void sendError(int error, String message) throws IOException {
		this.response.sendError(error, message);
	}

	public String getQueryString() {
		return this.request.getQueryString();
	}

	public UpreaderSession getSession(boolean create) {
		return this.application.getSessionManager().getSession(this.request, create);
	}

	public void setAttribute(String name, Object o) {
		this.request.setAttribute(name, o);
	}

	public Object getAttribute(String name) {
		return this.request.getAttribute(name);
	}

	public boolean isHead() {
		return StringHelper.equalsIgnoreCase(this.method, "HEAD");
	}

	public boolean isGet() {
		return StringHelper.equalsIgnoreCase(this.method, "GET");
	}

	public boolean isPost() {
		return StringHelper.equalsIgnoreCase(this.method, "POST");
	}

	public boolean isPut() {
		return StringHelper.equalsIgnoreCase(this.method, "PUT");
	}

	public boolean isDelete() {
		return StringHelper.equalsIgnoreCase(this.method, "DELETE");
	}

	public boolean isTrace() {
		return StringHelper.equalsIgnoreCase(this.method, "TRACE");
	}

	public boolean isOptions() {
		return StringHelper.equalsIgnoreCase(this.method, "OPTIONS");
	}

	public boolean isConnect() {
		return StringHelper.equalsIgnoreCase(this.method, "CONNECT");
	}

	public boolean isPatch() {
		return StringHelper.equalsIgnoreCase(this.method, "PATCH");
	}

	public void setRewritten(boolean rewritten) {
		this.rewritten = rewritten;
		if ((rewritten) && (this.rewrittenParameters == null)) {
			this.rewrittenParameters = new HashMap();
		}
	}

	public boolean hasJspBeenIncluded() {
		return this.jspHasBeenIncluded;
	}

	public void setStatus(int status) {
		this.response.setStatus(status);
	}

	public String getRequestSignature() {
		Object forwardURI = this.request.getAttribute("javax.servlet.forward.request_uri");
		if (forwardURI == null) {
			String queryString = this.request.getQueryString();
			if (StringHelper.isNonEmpty(queryString)) {
				return this.request.getRequestURL() + "?" + queryString;
			}

			return this.request.getRequestURL().toString();
		}

		StringBuilder sb = new StringBuilder();
		sb.append(this.request.getScheme());
		sb.append("://");
		sb.append(this.request.getServerName());
		int port = this.request.getServerPort();

		if ((port > 0) && (port != 80) && (port != 443)) {
			sb.append(":");
			sb.append(port);
		}

		sb.append(forwardURI);

		Object forwardQueryString = this.request.getAttribute("javax.servlet.forward.query_string");
		if (forwardQueryString != null) {
			sb.append("?");
			sb.append(forwardQueryString);
		}

		return sb.toString();
	}

	public boolean includeFile(File file, String fileName, boolean asAttachment, String contentType) {
		boolean setContentType = false;
		ServletInitConfig servletConfig = application.getServletConfig();
		if (servletConfig != null) {
			if (StringHelper.isNonEmpty(contentType)) {
				setContentType(contentType);
				setContentType = true;
			} else {
				String mimeType = servletConfig.getMimeType(fileName);
				if (mimeType != null) {
					setContentType(mimeType);
					setContentType = true;
				}
			}
		}

		if (!setContentType)
			setContentType("application/octet-stream;name=\"" + fileName + "\"");
		if (asAttachment)
			setResponseHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");

		byte[] buffer = new byte[4096];
		FileInputStream fis = null;
		OutputStream os = null;

		try {
			fis = new FileInputStream(file);
			os = getOutputStream();

			int read;
			for (; fis.available() > 0; os.write(buffer, 0, read))
				read = fis.read(buffer);

			return true;
		} catch (IOException e) {
			log.debug("IOException while including file.", e);
			return false;
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
				}

			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
				}
		}
	}

	public Infrastructure getInfrastructure() {
		return infrastructure;
	}

	public Principal getPrincipal() {
		return request.getUserPrincipal();
	}
}