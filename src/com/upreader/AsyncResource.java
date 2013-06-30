package com.upreader;

/**
 * Generic contract for resources that will start after the application initialization, in the same thread.
 * For starting in a separate thread, please use DeferredStartAsync
 * 
 * @author Flavius
 * 
 */
public interface AsyncResource {
	public void begin();
	public void end();
}
