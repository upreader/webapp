package com.upreader;

import java.io.IOException;
import java.io.Writer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.caucho.server.webapp.ForwardRequest;
import com.upreader.context.Context;
import com.upreader.helper.CollectionHelper;

/**
 * Main application servlet that handles application initialization and request
 * processing
 * 
 * @author Flavius
 */
@SuppressWarnings("serial")
@WebServlet(name = "upreader", urlPatterns = "/*", initParams = { @WebInitParam(name = "ConfigurationFile", value = "upreader.conf") })
public class UpreaderServlet extends HttpServlet {
	private Logger log = Logger.getLogger(UpreaderServlet.class);
	
	@Inject @Named("jdbc/mysql")
	private DataSource dataSource;
	
	private final UpreaderApplication application = getApplication();
	private final UpreaderVersion version = this.application.getVersion();
	private RequestListener[] listeners = new RequestListener[0];

	@Override
	public void init() throws ServletException {
		this.application.setServlet(this);
		ServletInitConfig config = ServletInitConfig.createHttpInitConfig(getServletConfig(), getServletContext());
		this.application.initialize(config);
	}

	@Override
	public void destroy() {
		super.destroy();
		this.application.shutdown();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (this.application.isRunning()) {
			UpreaderRequest httpRequest = new UpreaderRequest(request, response, request.getServletContext(), this.application);
			for (int i = 0; i < this.listeners.length; i++) {
				try {
					this.listeners[i].requestStarting(httpRequest);
				} catch (Exception ex) {
				}
			}

			Context context = this.application.getContext(httpRequest);

			for (int i = 0; i < this.listeners.length; i++) {
				try {
					this.listeners[i].contextCreated(context);
				} catch (Exception ex) {
				}

			}

			try {
				handleRequest(context);
			} finally {
				for (int i = 0; i < this.listeners.length; i++) {
					try {
						this.listeners[i].requestCompleting(context.getRequest(), context);
					} catch (Exception ex) {
					}
				}
			}
		} else if (this.application.getState() == UpreaderApplication.OperationalState.NEW) {
			handleError(request, response, "Upreader not yet initialized.");
		} else {
			handleError(request, response, "Upreader not running.");
		}
	}

	public void handleRequest(Context context) {
		try {
			this.application.getDispatcher().dispatch(context);
		} finally {
			this.application.getDispatcher().dispatchComplete(context);
			Context.complete();
		}
	}

	public void handleError(HttpServletRequest request, HttpServletResponse response, String error) throws IOException {
		response.setContentType("text/html");
		Writer writer = response.getWriter();
		writer.write("<html>");
		writer.write("<head><title>Temporarily Unavailable</title>");
		writer.write("<style>");
		writer.write("body { background-color: white; color: black; }");
		writer.write("p { font-family: Arial, Helvetica, Sans-serif; font-size: 12px; }");
		writer.write("h2 { font-family: Arial, Helvetica, Sans-serif; font-size: 14px; font-weight: bold; }");
		writer.write("pre { font-size: 9px; }");
		writer.write("</style>");
		writer.write("</head>");
		writer.write("<body>");
		writer.write("<h2>Temporarily Unavailable</h2>\r\n");
		writer.write("<p>Upreader is temporarily unavailable due to maintenance work.  Please check back later.</p>\r\n");
		writer.write("<!-- " + error + " -->" + "\r\n");
		writer.write("</body></html>");
	}

	@Override
	public String getServletName() {
		return this.getClass().getSimpleName() + " Version " + this.version.getVersionString();
	}

	@Override
	public String getServletInfo() {
		return getServletName() + " - Copyright (c) 2013 Upreader Ltd.";
	}

	public void addRequestListener(RequestListener listener) {
		if (this.listeners.length == 0 || !CollectionHelper.arrayContains(this.listeners, listener)) {
			RequestListener[] temp = new RequestListener[this.listeners.length + 1];
			System.arraycopy(this.listeners, 0, temp, 0, this.listeners.length);
			temp[(temp.length - 1)] = listener;
			this.listeners = temp;
		}
	}

	public UpreaderApplication getApplication() {
		return UpreaderApplication.getInstance();
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
}
