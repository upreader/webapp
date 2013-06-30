package com.upreader;

import com.upreader.context.Context;

/**
 * Listener for the request lifecycle.
 * 
 * Interested parties can register in the UpreaderServlet for notifications.
 * 
 * @author Flavius
 */
public interface RequestListener {
	public abstract void requestStarting(UpreaderRequest paramRequest);

	public abstract void contextCreated(Context paramContext);

	public abstract void requestCompleting(UpreaderRequest paramRequest, Context paramContext);
}
