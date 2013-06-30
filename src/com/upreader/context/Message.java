package com.upreader.context;

import java.io.Serializable;

import com.upreader.collection.StringList;
import com.upreader.helper.StringHelper;

/**
 * HTML Message attached to a Context.
 * 
 * Supports text and styling and it's useful for sending information (Errors,
 * Warnings, Success etc.)
 * 
 * @author Flavius
 * 
 */
public class Message implements Serializable {
	private static final long serialVersionUID = 2151370721184063723L;

	private final String id;
	private final StringList classNames = new StringList(" ");
	private final String message;
	private final MessageType type;

	public Message(String message) {
		this.id = null;
		this.message = message;
		this.type = MessageType.NORMAL;
		addClass(this.type.getClassName());
	}

	public Message(String message, MessageType type) {
		this.id = null;
		this.message = message;
		this.type = type;
		addClass(type.getClassName());
	}

	public Message(String id, String message, MessageType type) {
		this.id = StringHelper.trim(id);
		this.message = message;
		this.type = type;
		addClass(type.getClassName());
	}

	public Message(String message, MessageType type, String className) {
		this.id = null;
		this.message = message;
		this.type = type;
		this.classNames.add(className);
	}

	public String getId() {
		return this.id;
	}

	public StringList getClassNames() {
		return this.classNames;
	}

	public void addClass(String className) {
		getClassNames().add(className);
	}

	public String getMessage() {
		return this.message;
	}

	public MessageType getType() {
		return this.type;
	}
}
