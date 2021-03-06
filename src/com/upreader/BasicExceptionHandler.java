package com.upreader;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.upreader.context.Context;
import com.upreader.helper.StringHelper;
import com.upreader.util.Configurable;
import com.upreader.util.ConfigurationProperties;

/**
 * Standard exception handler that can write exceptions to the log or to a custom page
 * 
 * @author Flavius
 *
 */
public class BasicExceptionHandler implements ExceptionHandler, Configurable {
	private Logger log = Logger.getLogger(BasicExceptionHandler.class);
	private final UpreaderApplication application;
	private boolean displayExceptionsInLog = true;
	private boolean displayStackTracesInLog = false;
	private boolean revealStackTrace = true;
	private boolean useErrorCode500 = false;
	private String errorPage = null;

	public BasicExceptionHandler(UpreaderApplication application) {
		this.application = application;
		application.getConfigurator().addConfigurable(this);
	}

	@Override
	public void configure(ConfigurationProperties props) {
		this.displayExceptionsInLog = props.getYesNoProperty("LogExceptions", this.displayExceptionsInLog);
		this.displayStackTracesInLog = props.getYesNoProperty("LogStackTraces", this.displayStackTracesInLog);
		this.revealStackTrace = props.getYesNoProperty("RevealStackTrace", this.revealStackTrace);
		this.useErrorCode500 = props.getYesNoProperty("UseErrorCode500", this.useErrorCode500);
		this.errorPage = props.getProperty("ErrorPage", null);
	}

	@Override
	public void handleException(Context context, Throwable exception) {
		if (exception != null) {
			if ((exception instanceof ServletException)) {
				ServletException servletException = (ServletException) exception;
				logException(context, servletException.getRootCause());
			} else {
				logException(context, exception);
			}

			if (context != null) {
				includeErrorPage(context, exception);
			}
		} else {
			log.warn("Null exception passed to BasicExceptionHandler.  This should not happen.");
		}
	}

	protected void logException(Context context, Throwable exception) {
		if (this.displayExceptionsInLog) {
			log.error("Exception:\n" + exception);
		}

		if (this.displayStackTracesInLog) {
			log.error("Stack trace: " + convertStackTraceToString(exception));
		}
	}

	protected void includeErrorPage(Context context, Throwable exception) {
		if (this.useErrorCode500) {
			context.sendError(500);
		} else if (StringHelper.isNonEmpty(this.errorPage) && context.request().getAttribute("upreader.handled") == null) {
			context.request().setAttribute("upreader.handled", true);
			context.request().setAttribute("upreader.exception", true);
			context.request().setAttribute("upreader.stackTrace", convertStackTraceToString(exception));
			context.request().setAttribute("upreader.reveal", Boolean.valueOf(this.revealStackTrace));
			
			try {
				context.forward(errorPage);
			} catch (Exception exc) {
				outputDefaultErrorPage(context, exception);
			}
		} else {
			outputDefaultErrorPage(context, exception);
		}
	}

	protected void outputDefaultErrorPage(Context context, Throwable exception) {
		if (this.revealStackTrace) {
			context.print("<html>");
			context.print("<head><title>Internal error</title>");
			context.print("<style>");
			context.print("body { background-color: white; color: black; }");
			context.print("p { font-family: Arial, Helvetica, Sans-serif; font-size: 12px; }");
			context.print("h2 { font-family: Arial, Helvetica, Sans-serif; font-size: 14px; font-weight: bold; }");
			context.print("pre { font-size: 9px; }");
			context.print("</style>");
			context.print("</head>");
			context.print("<body>");
			context.print("<!-- BasicExceptionHandler -->");
			context.print("<h2>Internal error</h2>");
			context.print("<p>An exception was caught by the application infrastructure:</p>");
			context.print("<p><pre>");
			context.print(convertStackTraceToString(exception));
			context.print("");
			context.print("</pre></p>");
			if ((exception instanceof ServletException)) {
				ServletException servletException = (ServletException) exception;
				if (servletException.getRootCause() != null) {
					context.print("<p>Root cause:</p>");
					context.print("<p><pre>");
					context.print(convertStackTraceToString(servletException.getRootCause()));
					context.print("");
					context.print("</pre></p>");
				} else {
					context.print("<p>No root cause provided.</p>");
				}
			}
			context.print("</body>");
			context.print("</html>");
		} else {
			context.print("<html><head>");
			context.print("<title>Server Error</title>");
			context.print("<style>");
			context.print("body { background-color: white; color: black; }");
			context.print("p, div { color: white; font-family: Tahoma, Verdana, Arial, Helvetica, Sans-serif; font-size: 14px; }");
			context.print(".container { border: 8px solid #777777; width: 350px; }");
			context.print(".banner { background: #D06060; color: white; font-weight: bold; padding: 4px }");
			context.print(".text { background: #777777; padding: 4px; }");
			context.print("</style>");
			context.print("</head><body>");
			context.print("<!-- BasicExceptionHandler -->");
			context.print("<div class=\"container\">");
			context.print("<div class=\"banner\">Please bear with us...</div>");
			context.print("<div class=\"text\">We're sorry, our web site is not able to process your request correctly at this time.  Please try again at a later time.  If this situation persists, please get in touch with our customer service or technical support staff.</div>");
			context.print("</div>");
			context.print("<!--");
			context.print(convertStackTraceToString(exception));
			if ((exception instanceof ServletException)) {
				ServletException servletException = (ServletException) exception;
				context.print("Root cause:");
				context.print(convertStackTraceToString(servletException.getRootCause()));
			}
			context.print("-->");
			context.print("</body></html>");
		}
	}
	

	private String convertStackTraceToString(Throwable throwable) {
		String toReturn = "";

		if (throwable != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);

			throwable.printStackTrace(pw);

			toReturn = sw.toString();
		}

		return toReturn;
	}
}
