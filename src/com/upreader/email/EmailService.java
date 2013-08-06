package com.upreader.email;

import com.upreader.AsyncResource;
import com.upreader.UpreaderApplication;
import com.upreader.helper.StringHelper;
import com.upreader.thread.PausableThreadPoolExecutor;
import com.upreader.util.Configurable;
import com.upreader.util.ConfigurationProperties;
import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class EmailService implements AsyncResource, Configurable {
    private Logger log = Logger.getLogger(EmailService.class);

    private int senderThreads = 10;
    private volatile PausableThreadPoolExecutor executor;
    private final EmailTransport transport;
    private final AtomicInteger queued;
    private final AtomicInteger sent;
    private final AtomicInteger removed;

    private boolean enabled = true;

    public EmailService(UpreaderApplication application) {
        this.executor = new PausableThreadPoolExecutor(this.senderThreads);
        this.queued = new AtomicInteger();
        this.sent = new AtomicInteger();
        this.removed = new AtomicInteger();
        this.transport = new EmailTransport(application);

        application.addAsyncResource(this);
        application.getConfigurator().addConfigurable(this);
    }

    @Override
    public void begin() {
    }

    @Override
    public void end() {
        this.executor.shutdown();
    }

    @Override
    public void configure(ConfigurationProperties configurationProperties) {
    }

    public boolean sendMail(EmailMessage email) {
        return sendMail(email, true);
    }

    protected boolean sendMail(EmailMessage email, boolean incrementQueued) {
        if (this.enabled) {
            if (email == null) {
                this.log.debug("Email is null.  Cannot queue.");
                return false;
            }

            if ((StringHelper.isNonEmpty(email.getRecipient())) && (StringHelper.isNonEmpty(email.getAuthor()))) {
                Sender sender = new Sender(this, email);
                this.executor.submit(sender);
                if (incrementQueued) {
                    incrementQueued();
                }

                return true;
            }

            this.log.debug("Cannot send e-mail with empty author or recipient.");
            return false;
        }

        return true;

    }

    protected void incrementSent() {
        this.sent.incrementAndGet();
    }

    protected void incrementRemoved() {
        this.removed.incrementAndGet();
    }

    protected void incrementQueued() {
        this.queued.incrementAndGet();
    }

    public int getQueuedCount() {
        return this.queued.get();
    }

    public int getSentCount() {
        return this.sent.get();
    }

    public int getRemovedCount() {
        return this.removed.get();
    }

    public int getSenderThreadCount() {
        return this.executor.getActiveCount();
    }

    public int getPendingCount() {
        int currentSent = getSentCount();
        int currentQueued = getQueuedCount();
        int currentRemoved = getRemovedCount();

        return currentQueued - currentSent - currentRemoved;
    }

    public EmailTransport getTransport() {
        return transport;
    }

    public void isEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void pause() {
        this.executor.pause();
    }

    public void unpause() {
        this.executor.resume();
    }

    public boolean isPaused() {
        return this.executor.isPaused();
    }

    private static class Sender implements Runnable {
        private final EmailService service;
        private final EmailMessage email;

        public Sender(EmailService servicer, EmailMessage email) {
            this.service = servicer;
            this.email = email;
        }

        @Override
        public void run() {
            boolean success = this.service.getTransport().sendEmail(this.email);
            if (success) {
                this.service.incrementSent();
            } else {
                this.email.incrementDeliveryAttempts();
                debug("Mail to " + this.email.getRecipient() + " failed on try " + this.email.getDeliveryAttempts() + ".");

                if (this.email.getDeliveryAttempts() >= this.service.getTransport().getRetryLimit()) {
                    debug("Mail removed from queue.");
                    this.service.incrementRemoved();
                } else {
                    this.service.sendMail(this.email, false);
                    debug("Mail requeued.");
                }
            }
        }

        private void debug(String debug) {
            this.service.log.debug(debug);
        }
    }
}
