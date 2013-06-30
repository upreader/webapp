package com.upreader.mustache;

import java.util.List;

import com.upreader.context.Context;
import com.upreader.context.Message;

/**
 * Make the context-scoped objects availble for the Mustache template
 * 
 * @author Flavius
 *
 */
public class TemplateReferences {
	public final TemplateAppReferences app;
	//public final PyxisUser currentUser;
	public final Object req;
	public final List<Message> messages;

	public TemplateReferences(Context context, TemplateAppReferences applicationScope, Object requestScope) {
		this.app = applicationScope;
		this.req = requestScope;
		this.messages = context.messages().list();
		
		//if (applicationScope.application.getSecurity() != null) {
		//	this.currentUser = applicationScope.application.getSecurity().getUser(context);
		//} else {
		//	this.currentUser = null;
		//}
	}
}
