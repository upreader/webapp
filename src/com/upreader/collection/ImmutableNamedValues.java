package com.upreader.collection;

/**
 * Read-only contract for a name-value store
 * 
 * @author Flavius
 * 
 */
public interface ImmutableNamedValues {
	public boolean has(String name);
	public String get(String name);
	public String get(String name, String defaultValue);
	public int getInt(String name);
	public int getInt(String name, int defaultValue);
	public long getLong(String name);
	public long getLong(String name, long defaultValue);
	public boolean getBoolean(String name);
	public boolean getBoolean(String name, boolean defaultValue);
}
