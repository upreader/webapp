package com.upreader.email;

import com.upreader.helper.StringHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class EmailMessage {
    private String authorAddress = "";
    private String recipientAddress = "";
    private String recipientSource = "";
    private String bccRecipientAddress = "";
    private String subject = "";
    private String messageBody = "";
    private String htmlMessageBody = "";
    private String charset = "";
    private List<EmailAttachment> attachments = null;
    private Collection<EmailHeader> headers = null;
    private boolean sent = false;
    private boolean successful = false;
    private boolean hasHtmlBody = false;
    private boolean hasTextBody = false;
    private int deliveryAttempts = 0;
    private String mailServer;
    private EmailAuthenticator authenticator = null;

    public EmailMessage(String subject, String plainBody, String htmlBody, String recipient, String author) {
        setTextBody(plainBody);
        setHtmlBody(htmlBody);
        setSubject(subject);
        setRecipient(recipient);
        setAuthor(author);
        setHtmlEnabled(true);
    }

    public void setTextBody(String messageBody) {
        this.messageBody = messageBody;
        setTextEnabled(messageBody != null);
    }

    public void setHtmlBody(String messageBody) {
        this.htmlMessageBody = messageBody;
        setHtmlEnabled(messageBody != null);
    }

    public String getHtmlBody() {
        String tempTextBody = StringHelper.convertHTMLToText(this.htmlMessageBody).trim();
        if ((tempTextBody == null) || (tempTextBody.length() == 0)) {
            // fall-back to text body
            return getTextBody();
        }
        return this.htmlMessageBody;
    }

    public Collection<EmailHeader> getHeaders() {
        return this.headers;
    }

    public EmailHeader getHeader(String headerName) {
        if (this.headers != null) {
            for (Iterator<EmailHeader> it = this.headers.iterator(); it.hasNext(); ) {
                EmailHeader emailHeader = it.next();
                if (emailHeader.getHeaderName().equals(headerName)) {
                    return emailHeader;
                }
            }
        }
        return null;
    }

    public void setHeaders(Collection<EmailHeader> headers) {
        this.headers = headers;
    }

    public void addHeader(EmailHeader header) {
        if (this.headers == null) {
            this.headers = new ArrayList();
        }
        this.headers.add(header);
    }

    public void removeHeader(String headerName) {
        if (this.headers != null) {
            for (Iterator<EmailHeader> it = this.headers.iterator(); it.hasNext(); ) {
                EmailHeader header = it.next();
                if (header.getHeaderName().equals(headerName)) {
                    it.remove();
                }
            }
        }
    }

    public boolean hasAttachments() {
        return (this.attachments != null) && (!this.attachments.isEmpty());
    }

    public void addAttachment(EmailAttachment attachment) {
        if (attachment != null) {
            if (this.attachments == null) {
                this.attachments = new ArrayList<>();
            }
            this.attachments.add(attachment);
        }
    }

    public void setAttachments(Collection<EmailAttachment> pAttachments) {
        if (this.attachments != null) {
            Iterator<EmailAttachment> iter = pAttachments.iterator();
            while (iter.hasNext()) {
                this.attachments.add(iter.next());
            }
        } else {
            this.attachments = new ArrayList<>(pAttachments);
        }
    }

    public Collection<EmailAttachment> getAttachments() {
        return this.attachments;
    }

    public String getTextBody() {
        return this.messageBody;
    }

    public void setHtmlEnabled(boolean htmlFlag) {
        this.hasHtmlBody = htmlFlag;
    }

    public boolean isHtmlEnabled() {
        return this.hasHtmlBody;
    }

    public void setTextEnabled(boolean htmlFlag) {
        this.hasTextBody = htmlFlag;
    }

    public boolean isTextEnabled() {
        return this.hasTextBody;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRecipient() {
        return this.recipientAddress;
    }

    public void setRecipient(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public void setAuthor(String authorAddress) {
        this.authorAddress = authorAddress;
    }

    public String getAuthor() {
        return this.authorAddress;
    }

    public void incrementDeliveryAttempts() {
        this.deliveryAttempts += 1;
    }

    public int getDeliveryAttempts() {
        return this.deliveryAttempts;
    }

    public String getRecipientSource() {
        return this.recipientSource;
    }

    public void setRecipientSource(String recipientSource) {
        this.recipientSource = recipientSource;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setMailServer(String mailServer) {
        this.mailServer = mailServer;
    }

    public String getMailServer() {
        return this.mailServer;
    }

    public EmailAuthenticator getEmailAuthenticator() {
        return this.authenticator;
    }

    public void setEmailAuthenticator(EmailAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    public void setBccRecipient(String bccRecipientAddress) {
        this.bccRecipientAddress = bccRecipientAddress;
    }

    public String getBccRecipient() {
        return this.bccRecipientAddress;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean wasSent() {
        return this.sent;
    }

    public void setSuccessful(boolean success) {
        this.successful = success;
    }

    public boolean wasSuccessful() {
        return this.successful;
    }
}
