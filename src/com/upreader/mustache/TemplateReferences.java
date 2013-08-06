package com.upreader.mustache;

import java.util.List;

import com.upreader.context.Context;
import com.upreader.context.Message;

/**
 * Make the context-scoped objects available for the Mustache template
 * 
 * @author Flavius
 *
 */
public class TemplateReferences {
	public final TemplateAppReferences app;
	public final String currentUser;
	public final Object data;
	public final List<Message> messages;

	public TemplateReferences(Context context, TemplateAppReferences applicationScope, Object data) {
		this.app = applicationScope;
		this.data = data;
		this.messages = context.messages().list();
		this.currentUser = context.username();
	}
}
