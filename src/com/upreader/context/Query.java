package com.upreader.context;

import java.io.File;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.upreader.UpreaderRequest;
import com.upreader.collection.ImmutableNamedValues;
import com.upreader.helper.StringHelper;
import com.upreader.util.NumberHelper;

/**
 * HTTP GET parameters (URL Query)
 * 
 * @author Flavius
 * 
 */
public class Query implements ImmutableNamedValues {
	private final UpreaderRequest request;

	public Query(UpreaderRequest request) {
		this.request = request;
	}

	@Override
	public boolean has(String name) {
		return this.request.getParameter(name) != null;
	}

	@Override
	public String get(String name) {
		return get(name, null);
	}

	@Override
	public String get(String name, String defaultValue) {
		String value = this.request.getParameter(name);
		return value != null ? value : defaultValue;
	}

	public String[] getStrings(String name) {
		return this.request.getParameterValues(name);
	}

	@Override
	public int getInt(String name) {
		return getInt(name, 0);
	}

	@Override
	public int getInt(String name, int defaultValue) {
		return NumberHelper.parseInt(get(name), defaultValue);
	}

	@Override
	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}

	@Override
	public boolean getBoolean(String name, boolean defaultValue) {
		String value = this.request.getParameter(name);
		if (defaultValue) {
			return !"false".equals(value);
		}

		return "true".equals(value);
	}

	public boolean getBooleanLenient(String name, boolean defaultValue) {
		return StringHelper.parseBoolean(name, defaultValue);
	}

	@Override
	public long getLong(String name) {
		return getLong(name, 0L);
	}

	@Override
	public long getLong(String name, long defaultValue) {
		return NumberHelper.parseLong(get(name), defaultValue);
	}

	public float getFloat(String name) {
		return getFloat(name, 0);
	}

	public float getFloat(String name, float defaultValue) {
		return NumberHelper.parseFloat(get(name), defaultValue);
	}
}
