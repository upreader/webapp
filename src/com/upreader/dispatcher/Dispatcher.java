package com.upreader.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.upreader.ExceptionHandler;
import com.upreader.UpreaderApplication;
import com.upreader.context.Context;

/**
 * Dispatches requests to the appropriate methods based on path segments
 * 
 * @author Flavius
 * 
 */
public class Dispatcher {
	private Logger log = Logger.getLogger(Dispatcher.class);
	private UpreaderApplication application;
	private final Map<String, BasicPathHandler> handlers;
	private final BasicPathHandler defaultHandler;
	private final List<ExceptionHandler> exceptionHandlers;

	public Dispatcher(UpreaderApplication application, BasicPathHandler handler, ExceptionHandler exceptionHandler) {
		this.application = application;
		this.handlers = new HashMap<>();
		this.defaultHandler = handler;
		this.exceptionHandlers = new ArrayList<>();

		exceptionHandlers.add(exceptionHandler);

		if (this.defaultHandler == null) {
			throw new IllegalArgumentException("Configuration did not provide a default handler.");
		}
		if (this.exceptionHandlers.size() == 0 || exceptionHandler == null) {
			throw new IllegalArgumentException("PathDispatcher must be configured with at least one ExceptionHandler.");
		}
	}

	public BasicPathHandler get(String rootPathSegment) {
		return this.handlers.get(rootPathSegment);
	}

	public UpreaderApplication getApplication() {
		return application;
	}

	public boolean dispatch(Context plainContext) {
		boolean success = false;
		try {
			Context context = plainContext;
			PathSegments segments = new PathSegments(context.getRequestUri());
			BasicPathHandler handler = null;
			if (segments.getCount() > 0) {
				handler = get(segments.get(0));
				segments.increaseOffset();
			}

			if (handler == null) {
				handler = this.defaultHandler;
			}

			try {
				success = handler.prehandle(segments, context);
			} finally {
				if (!success) {
					handler.posthandle(segments, context);
				}

			}

			try {
				if (!success) {
					handler = this.defaultHandler;

					handler.prehandle(segments.offset(0), context);
				}

				success = handler.handle(segments, context);
			} finally {
				handler.posthandle(segments, context);
			}

			if ((!success) && (handler != this.defaultHandler)) {
				this.defaultHandler.prehandle(segments.offset(0), context);
				try {
					success = this.defaultHandler.handle(segments, context);
				} finally {
					this.defaultHandler.posthandle(segments, context);
				}
			}
		} catch (Throwable exc) {
			dispatchException(plainContext, exc);
		}

		return success;
	}

	public void dispatchComplete(Context context) {
	}

	public void renderStarting(Context context, String jspName) {
	}

	public void renderComplete(Context context) {
	}

	public void dispatchException(Context context, Throwable exception) {
		try {
			for (ExceptionHandler handler : this.exceptionHandlers) {
				handler.handleException(context, exception);
			}

		} catch (Exception exc) {
			log.debug("Exception encountered while processing earlier " + exception, exc);
		}
	}
}
