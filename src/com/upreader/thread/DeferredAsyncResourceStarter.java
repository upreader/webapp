package com.upreader.thread;

import java.util.List;

import com.upreader.AsyncResource;

/**
 * Separate thread for starting async resources after 2000ms the application officially started
 * 
 * @author Flavius
 *
 */
public class DeferredAsyncResourceStarter extends Thread {
	private final List<DeferredAsyncResource> deferredAsyncResources;

	public DeferredAsyncResourceStarter(List<DeferredAsyncResource> deferredAsyncResources) {
		super("Deferred Asynchronous Object Starter");
		this.deferredAsyncResources = deferredAsyncResources;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException interruptedexception) {
		}

		for (AsyncResource asynch : this.deferredAsyncResources) {
			asynch.begin();
		}
	}
}
