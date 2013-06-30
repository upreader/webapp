package com.upreader;

/**
 * Tasks that will run when 
 * @author Flavius
 *
 */
public interface InitializationTask {
	public void taskInitialize(UpreaderApplication application);
	public void taskRetryInitialization(UpreaderApplication application, int attemptNo);
	
	public boolean isTaskReady(UpreaderApplication application);
}
