package com.upreader.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread pool executor than can be paused and then resumed
 *
 * @author Flavius Burca
 */
public class PausableThreadPoolExecutor extends ThreadPoolExecutor {
    private boolean paused;
    private final ReentrantLock pauseLock = new ReentrantLock();
    private final Condition unpaused = this.pauseLock.newCondition();

    public PausableThreadPoolExecutor(int coreThreads) {
        super(coreThreads, coreThreads, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        this.pauseLock.lock();
        try {
            while (this.paused) {
                this.unpaused.await();
            }
        } catch (InterruptedException ie) {
            t.interrupt();
        } finally {
            this.pauseLock.unlock();
        }
    }

    public void pause() {
        this.pauseLock.lock();
        try {
            this.paused = true;
        } finally {
            this.pauseLock.unlock();
        }
    }

    public void resume() {
        this.pauseLock.lock();
        try {
            this.paused = false;
            this.unpaused.signalAll();
        } finally {
            this.pauseLock.unlock();
        }
    }

    public boolean isPaused() {
        return this.paused;
    }
}

