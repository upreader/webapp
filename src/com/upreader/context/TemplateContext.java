package com.upreader.context;

import java.util.Iterator;
import java.util.Map;

import com.upreader.collection.MutableNamedObjects;

/**
 * Mustache template context that is added when rendering a renderMustacheTemplate template
 * 
 * @author Flavius
 *
 */
public class TemplateContext extends MutableNamedObjects {	
	public MutableNamedObjects putObject(String name, Object value) {
		if ((value == null) || (name == null)) {
			throw new IllegalArgumentException("context.putDelivery does not support null keys or values (" + name + ": " + value
					+ ")");
		}

		return super.putObject(name, value);
	}

	public TemplateContext putAll(Map<String, Object> map) {
		for (Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = it.next();
			putObject(entry.getKey(), entry.getValue());
		}
		return this;
	}

}
