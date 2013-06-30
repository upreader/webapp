package com.upreader.thread;

import com.upreader.AsyncResource;

public class EndableThread extends Thread implements AsyncResource {
	public static final int DEFAULT_MAXIMUM_SLEEP = 10000;
	public static final int DEFAULT_MINIMUM_SLEEP = 500;
	public static final int DEFAULT_SLEEP_ADJUSTMENT = 500;
	public static final int DEFAULT_SLEEP_PERIOD = 1000;

	private volatile boolean running = true;
	private volatile boolean paused = false;
	private final int maxSleep;
	private final int minSleep;
	private final int sleepAdjustment;
	private boolean sleeping = false;
	private boolean pauseChecked = false;

	private int sleepPeriod = DEFAULT_SLEEP_PERIOD;
	private long startTime = 0L;
	private long stopTime = 0L;

	public EndableThread(String name, int sleepPeriod, int maxSleep, int minSleep, int sleepAdjustment) {
		super(name);

		this.sleepPeriod = sleepPeriod;
		this.maxSleep = maxSleep;
		this.minSleep = minSleep;
		this.sleepAdjustment = sleepAdjustment;
	}

	public EndableThread(int sleepPeriod, int maxSleep, int minSleep, int sleepAdjustment) {
		this("Endable Thread", sleepPeriod, maxSleep, minSleep, sleepAdjustment);
	}

	public EndableThread(String name, int sleepPeriod) {
		this(name, sleepPeriod, DEFAULT_MAXIMUM_SLEEP, DEFAULT_MINIMUM_SLEEP, DEFAULT_SLEEP_ADJUSTMENT);
	}

	public EndableThread(String name) {
		this(name, DEFAULT_SLEEP_PERIOD);
	}

	public EndableThread() {
		this("Endable thread");
	}

	public void setKeepRunning(boolean keepRunning) {
		this.running = keepRunning;
		interrupt();

		if (!keepRunning) {
			setStopTime();
		}
	}

	public void setPaused(boolean paused) {
		if ((this.paused) && (!paused)) {
			synchronized (this) {
				this.paused = paused;
				this.pauseChecked = false;
				notifyAll();
			}

		}

		this.paused = paused;
	}

	public boolean checkPause() {
		if (this.paused) {
			this.pauseChecked = true;
			try {
				while (this.paused) {
					synchronized (this) {
						if (this.paused) {
							wait(10000L);
						}
					}
				}
			} catch (InterruptedException localInterruptedException) {
			}

		}

		return isRunning();
	}

	public void setStartTime() {
		this.startTime = System.currentTimeMillis();
	}

	public long getStartTime() {
		return this.startTime;
	}

	public boolean isRunning() {
		return running;
	}

	public void setStopTime() {
		this.stopTime = System.currentTimeMillis();
	}

	public long getStopTime() {
		return this.stopTime;
	}

	protected int getMaxSleep() {
		return this.maxSleep;
	}

	public long getThreadLifetime() {
		if (getStopTime() >= getStartTime()) {
			return getStopTime() - getStartTime();
		}

		return System.currentTimeMillis() - getStartTime();
	}

	public void begin() {
		if (getState() == Thread.State.NEW) {
			setStartTime();
			start();
		}
	}

	public void end() {
		setKeepRunning(false);
	}

	public boolean isPaused() {
		return this.paused;
	}

	public boolean isPauseChecked() {
		return this.pauseChecked;
	}

	public boolean isAsleep() {
		return this.sleeping;
	}

	public int getSleepPeriod() {
		return this.sleepPeriod;
	}

	public void simpleSleep() {
		simpleSleep(this.sleepPeriod);
	}

	public void simpleSleep(int milliseconds) {
		simpleSleep((long)milliseconds);
	}

	public void simpleSleep(long milliseconds) {
		if (milliseconds > 0L) {
			this.sleeping = true;
			try {
				Thread.sleep(milliseconds);
			} catch (InterruptedException localInterruptedException) {
			}

			this.sleeping = false;
		} else {
			Thread.yield();
		}
	}

	public void incrementSleep() {
		if (this.sleepPeriod < this.maxSleep) {
			this.sleepPeriod += this.sleepAdjustment;
			if (this.sleepPeriod > this.maxSleep) {
				this.sleepPeriod = this.maxSleep;
			}
		}
	}

	public void decrementSleep() {
		if (this.sleepPeriod > this.minSleep) {
			this.sleepPeriod -= this.sleepAdjustment;
			if (this.sleepPeriod < this.minSleep) {
				this.sleepPeriod = this.minSleep;
			}
		}
	}

	public void setMinimumSleep() {
		this.sleepPeriod = this.minSleep;
	}

	public void setMaximumSleep() {
		this.sleepPeriod = this.maxSleep;
	}

}
