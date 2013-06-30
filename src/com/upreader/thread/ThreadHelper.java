package com.upreader.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadHelper {
	private static final ExecutorService JOB_EXECUTOR = Executors.newCachedThreadPool();
	private static final ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newScheduledThreadPool(2);

	public static Future<?> submit(Callable<?> callable) {
		return JOB_EXECUTOR.submit(callable);
	}

	public static void submit(Runnable runnable) {
		JOB_EXECUTOR.execute(runnable);
	}

	public static Future<?> schedule(Callable<?> callable, long delay, TimeUnit unit) {
		return SCHEDULED_EXECUTOR.schedule(callable, delay, unit);
	}

	public static void schedule(Runnable runnable, long delay, TimeUnit unit) {
		SCHEDULED_EXECUTOR.schedule(runnable, delay, unit);
	}
}
