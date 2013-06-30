package com.upreader.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import com.upreader.helper.CollectionHelper;
import com.upreader.helper.StringHelper;

/**
 * A list of strings separated by a separator and a final separator
 * 
 * @author Flavius
 *
 */
public class StringList implements Serializable {
	private static final long serialVersionUID = 4861957609873080410L;

	private final StringBuilder value = new StringBuilder();
	private final String separator;
	private final String prefix;
	private final String suffix;
	private final String finalSeparator;
	private int lastInsertionPoint = 0;
	private int size = 0;

	public StringList() {
		this.separator = ",";
		this.prefix = "";
		this.suffix = "";
		this.finalSeparator = null;
	}

	public StringList(String separator) {
		this.separator = separator;
		this.prefix = "";
		this.suffix = "";
		this.finalSeparator = null;
	}

	public StringList(String separator, String prefix, String suffix) {
		this.separator = separator;
		this.suffix = suffix;
		this.prefix = prefix;
		this.finalSeparator = null;
	}

	public StringList(String separator, String prefix, String suffix, String finalSeparator) {
		this.separator = separator;
		this.suffix = suffix;
		this.prefix = prefix;
		this.finalSeparator = finalSeparator;
	}

	public static StringList getPlainEnglishList() {
		return new StringList(", ", "", "", " and ");
	}

	public static StringList getSemicolonEnglishList() {
		return new StringList("; ", "", "", " and ");
	}

	public void add(String toAdd) {
		if (this.size > 0) {
			this.value.append(this.separator);
		}

		this.size += 1;

		this.lastInsertionPoint = (this.value.length() - this.separator.length());

		this.value.append(this.prefix);
		this.value.append(toAdd);
		this.value.append(this.suffix);
	}

	public void add(int toAdd) {
		add(Integer.toString(toAdd));
	}

	public void addNonEmpty(String toAdd) {
		if (StringHelper.isNonEmpty(toAdd)) {
			add(toAdd);
		}
	}

	public void addAll(String[] toAdd) {
		if (CollectionHelper.isNonEmpty(toAdd)) {
			for (int i = 0; i < toAdd.length; i++) {
				add(toAdd[i]);
			}
		}
	}

	public void addAll(Collection<String> toAdd) {
		if (CollectionHelper.isNonEmpty(toAdd)) {
			for (Iterator it = toAdd.iterator(); it.hasNext();) {
				add((String) it.next());
			}
		}
	}

	public int length() {
		return this.value.length() + (this.finalSeparator != null ? this.finalSeparator.length() : 0);
	}

	public String toString() {
		if ((this.finalSeparator != null) && (this.lastInsertionPoint > 0)) {
			StringBuilder toReturn = new StringBuilder(this.value);
			toReturn.delete(this.lastInsertionPoint, this.lastInsertionPoint + this.separator.length());
			toReturn.insert(this.lastInsertionPoint, this.finalSeparator);
			return toReturn.toString();
		}

		return this.value.toString();
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}
}
