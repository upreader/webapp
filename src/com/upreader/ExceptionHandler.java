package com.upreader;

import com.upreader.context.Context;

/**
 * Handler for application exceptions
 * 
 * @author Flavius
 *
 */
public interface ExceptionHandler {
	public void handleException(Context context, Throwable exception);

	public void handleException(Context context, Throwable exception, String description);
}
