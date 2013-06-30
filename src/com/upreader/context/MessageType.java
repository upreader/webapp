package com.upreader.context;

/**
 * Types of messages based on CSS styling
 * 
 * @author Flavius
 *
 */
public enum MessageType {
	SUCCESS("success message", "background: #e6efc2; color: #264409; border-color: #c6d880;"), ERROR("error message",
			"background: #fbe3e4; color: #8a1f11; border-color: #fbc2c4;"),

	WARNING("warning message", "background: #fff6bf; color: #514721; border-color: #ffd324;"),

	NORMAL("normal message", "background: #d5edf8; color: #205791; border-color: #92cae4;");
	
	private static final String STYLE = "padding: 13px; margin-bottom: 16px; border: 2px solid #ddd;";

	private String className;
	private String style;

	private MessageType(String className, String style) {
		this.className = className;
		this.style = style;
	}

	protected String getClassName() {
		return this.className;
	}

	protected String getInlineStyle() {
		return STYLE + this.style;
	}
}
