package com.upreader.email;

import com.upreader.UpreaderApplication;
import com.upreader.helper.StringHelper;
import com.upreader.util.Configurable;
import com.upreader.util.ConfigurationProperties;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

public class EmailTransport implements Configurable {
    private Logger log = Logger.getLogger(EmailTransport.class);

    private int maximumRetries = 10;
    private Authenticator defaultAuthenticator = null;
    private EmailServerDescriptor[] outboundServers = new EmailServerDescriptor[0];

    public EmailTransport(UpreaderApplication application) {
        application.getConfigurator().addConfigurable(this);
    }

    @Override
    public void configure(ConfigurationProperties configurationProperties) {
    }

    public boolean sendEmail(EmailMessage email) {
        if (email != null) {
            String charsetString = "";
            if (StringHelper.isNonEmpty(email.getCharset())) {
                charsetString = "; charset=" + email.getCharset();
            }

            int tryNumber = email.getDeliveryAttempts();
            Session mailSession = null;
            if (email.getMailServer() == null) {
                EmailServerDescriptor[] servers = getOutboundServers();
                if (servers.length > 0) {
                    mailSession = servers[(tryNumber % servers.length)].getSession();
                } else {
                    log.debug("Mail failed to send because there are no servers defined.");
                    return false;
                }
            } else {
                if (StringHelper.isNonEmpty(email.getRecipientSource())) {
                    for (EmailServerDescriptor descriptor : getOutboundServers()) {
                        if ((descriptor.getServerAddress().equalsIgnoreCase(email.getMailServer())) &&
                                (email.getRecipientSource().equalsIgnoreCase(descriptor.getProperties().getProperty("mail.smtp.recipientsource")))) {
                            mailSession = descriptor.getSession();
                            break;
                        }

                    }
                }

                if (mailSession == null) {
                    for (EmailServerDescriptor descriptor : getOutboundServers()) {
                        if ((descriptor.getServerAddress().equalsIgnoreCase(email.getMailServer())) &&
                                (StringHelper.isEmpty(descriptor.getProperties().getProperty("mail.smtp.recipientsource")))) {
                            mailSession = descriptor.getSession();
                            break;
                        }
                    }
                }

                if (mailSession == null) {
                    Properties props = new Properties();
                    props.put("mail.smtp.host", email.getMailServer());
                    props.put("mail.smtp.connectiontimeout", "100000");
                    props.put("mail.smtp.timeout", "100000");

                    if ((this.defaultAuthenticator != null) || (email.getEmailAuthenticator() != null)) {
                        props.put("mail.smtp.auth", "true");
                    }

                    if (email.getEmailAuthenticator() != null) {
                        mailSession = Session.getInstance(props, email.getEmailAuthenticator());
                    } else {
                        mailSession = Session.getInstance(props, this.defaultAuthenticator);
                    }
                }
            }

            MimeMessage message = null;
            boolean alternativeEmail = (email.isTextEnabled()) && (email.isHtmlEnabled());

            try {
                message = new MimeMessage(mailSession);
                message.addFrom(InternetAddress.parse(email.getAuthor(), false));
                message.setReplyTo(InternetAddress.parse(email.getAuthor(), false));

                message.addRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(email.getRecipient(), false));

                if (StringHelper.isNonEmpty(email.getBccRecipient())) {
                    message.addRecipients(Message.RecipientType.BCC,
                            InternetAddress.parse(email.getBccRecipient(), false));
                }

                message.setSubject(email.getSubject(), email.getCharset());

                if ((email.getHeaders() != null) && (!email.getHeaders().isEmpty())) {
                    for (Iterator<EmailHeader> it = email.getHeaders().iterator(); it.hasNext(); ) {
                        EmailHeader emailHeader = it.next();
                        message.addHeader(emailHeader.getHeaderName(), emailHeader.getHeaderValue());
                    }
                } else {
                    message.addHeader("X-Gemini-EBT", email.getRecipient());
                }

                if ((alternativeEmail) || (email.hasAttachments())) {
                    MimeMultipart multipart;

                    if (alternativeEmail) {
                        multipart = new MimeMultipart("alternative");

                        MimeBodyPart textPart = new MimeBodyPart();
                        textPart.setContent(email.getTextBody(), "text/plain" + charsetString);
                        multipart.addBodyPart(textPart);

                        MimeBodyPart htmlPart = new MimeBodyPart();
                        htmlPart.setContent(email.getHtmlBody(), "text/html" + charsetString);
                        multipart.addBodyPart(htmlPart);
                    } else {
                        multipart = new MimeMultipart();

                        MimeBodyPart thePart = new MimeBodyPart();
                        if (email.isHtmlEnabled()) {
                            thePart.setContent(email.getHtmlBody(), "text/html" + charsetString);
                        } else {
                            thePart.setContent(email.getTextBody(), "text/plain" + charsetString);
                        }
                        multipart.addBodyPart(thePart);
                    }

                    if ((email.hasAttachments()) && (alternativeEmail)) {
                        Object mixed = new MimeMultipart("mixed");
                        MimeBodyPart wrap = new MimeBodyPart();
                        wrap.setContent(multipart);
                        ((Multipart) mixed).addBodyPart(wrap);

                        Collection attachments = email.getAttachments();
                        Iterator iter = attachments.iterator();
                        while (iter.hasNext()) {
                            MimeBodyPart filePart = new MimeBodyPart();
                            EmailAttachment attachment = (EmailAttachment) iter.next();

                            if ((attachment.getObjectAttachment() instanceof Message)) {
                                filePart.setContent(attachment.getObjectAttachment(), "message/rfc822");
                                filePart.setFileName(attachment.getName());
                            } else {
                                File file = attachment.getFile();
                                DataHandler dh = new DataHandler(new FileDataSource(file));
                                if (attachment.getName() != null) {
                                    filePart.setFileName(attachment.getName());
                                } else {
                                    filePart.setFileName(file.getName());
                                }
                                filePart.setDataHandler(dh);
                            }

                            ((Multipart) mixed).addBodyPart(filePart);
                        }
                        message.setContent((Multipart) mixed);
                    } else if (email.hasAttachments()) {
                        Collection attachments = email.getAttachments();
                        Iterator iter = attachments.iterator();
                        while (iter.hasNext()) {
                            EmailAttachment attachment = (EmailAttachment) iter.next();
                            MimeBodyPart filePart = new MimeBodyPart();

                            if ((attachment.getObjectAttachment() instanceof Message)) {
                                filePart.setContent(attachment.getObjectAttachment(), "message/rfc822");
                                filePart.setFileName(attachment.getName());
                            } else {
                                File file = attachment.getFile();
                                DataHandler dh = new DataHandler(new FileDataSource(file));
                                filePart.setFileName(file.getName());
                                filePart.setDataHandler(dh);
                            }
                            multipart.addBodyPart(filePart);
                        }
                        message.setContent(multipart);
                    } else {
                        message.setContent(multipart);
                    }

                } else if (email.isHtmlEnabled()) {
                    message.setContent(email.getTextBody(), "text/html" + charsetString);
                } else {
                    message.setContent(email.getTextBody(), "text/plain" + charsetString);
                }

                try {
                    Transport.send(message);
                    email.setSent(true);
                    message = null;
                    return true;
                } catch (MessagingException exc) {
                    email.setSent(false);
                    this.log.debug("Exception during JavaMail transport: " + exc);
                }
            } catch (MessagingException exc) {
                email.setSent(false);
                this.log.debug("Exception prior to JavaMail transport:" + exc);
            } finally {
                message = null;
            }
        } else {
            this.log.debug("Email is null!");
        }

        return false;
    }

    public EmailServerDescriptor[] getOutboundServers() {
        return this.outboundServers;
    }

    public int getRetryLimit() {
        return this.maximumRetries;
    }
}
