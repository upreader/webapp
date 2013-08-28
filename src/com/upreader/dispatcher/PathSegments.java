package com.upreader.dispatcher;

import com.upreader.collection.ImmutableNamedStrings;
import com.upreader.collection.ImmutableNamedValues;
import com.upreader.helper.StringHelper;
import com.upreader.helper.NumberHelper;

public class PathSegments {
	private final String uri;
	private final String[] segments;
	private int offset = 0;
	private ImmutableNamedStrings namedSegments;

	public PathSegments(String uri) {
		if ((uri.length() > 1) && (uri.charAt(0) == '/')) {
			this.uri = uri.substring(1);
		} else {
			this.uri = uri;
		}

		this.segments = this.uri.split("/");
	}

	protected PathSegments offset(int newOffset) {
		this.offset = NumberHelper.boundInteger(newOffset, 0, this.segments.length);
		return this;
	}

	protected PathSegments increaseOffset() {
		this.offset += 1;

		if (this.offset >= this.segments.length) {
			this.offset = (this.segments.length - 1);
		}
		return this;
	}

	protected PathSegments decreaseOffset() {
		this.offset -= 1;

		if (this.offset < 0) {
			this.offset = 0;
		}
		return this;
	}

	protected PathSegments assignName(int index, String name) {
		if (this.namedSegments == null) {
			this.namedSegments = new ImmutableNamedStrings(Math.max(this.segments.length, 100));
		}

		int assignIndex = index + this.offset;
		if ((assignIndex >= 0) && (assignIndex < this.segments.length)) {
			this.namedSegments.put(name, this.segments[assignIndex]);
		}

		return this;
	}

	protected ImmutableNamedValues getArguments() {
		if (this.namedSegments == null) {
			this.namedSegments = new ImmutableNamedStrings(0);
		}

		return this.namedSegments.seal();
	}

	public String getUri() {
		return this.uri;
	}

	public String getUriFromRoot() {
		return "/" + this.uri;
	}

	public String getUriBelowOffset() {
		if (this.offset == 0) {
			return "/";
		}

		StringBuilder toReturn = new StringBuilder(this.uri.length() + 1);
		for (int i = 0; i < this.offset; i++) {
			toReturn.append('/');
			toReturn.append(this.segments[i]);
		}

		return toReturn.toString();
	}
	
	public String getUriAboveOffset() {
		return getUriAboveOffset(this.offset);
	}
	
	public String getUriAboveOffset(int offset) {
		StringBuilder toReturn = new StringBuilder(this.uri.length() + 1);
		for (int i = offset; i < segments.length; i++) {
			toReturn.append(this.segments[i]);
			toReturn.append('/');
		}
		return toReturn.toString();
	}
	
	public int getCount() {
		return this.segments.length - this.offset;
	}

	public String get(int index) {
		int getIndex = index + this.offset;

		if ((getIndex >= 0) && (getIndex < this.segments.length)) {
			return this.segments[getIndex];
		}

		return null;
	}

	public String get(int index, String defaultValue) {
		String rawValue = get(index);
		return rawValue == null ? defaultValue : rawValue;
	}

	public int getInt(int index, int defaultValue) {
		return NumberHelper.parseInt(get(index), defaultValue);
	}

	public int getInt(int index) {
		return getInt(index, 0);
	}

	public String toString() {
		return "PathSegments [" + StringHelper.join(" / ", this.segments) + "]";
	}
}
