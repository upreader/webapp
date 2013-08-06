package com.upreader.email;

import com.upreader.helper.StringHelper;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.util.Properties;

public class EmailServerDescriptor {
    private final String serverAddress;
    private final int smtpPort;
    private final Properties serverProps;
    private final Authenticator authenticator;
    private final int timeoutProtoInit;
    private final int timeoutSocket;

    public EmailServerDescriptor(Properties props) {
        this.serverAddress = props.getProperty("ServerAddress", "");
        this.smtpPort = Integer.valueOf(props.getProperty("SmtpPort", "25"));
        this.timeoutProtoInit = Integer.valueOf(props.getProperty("MailProtocolInitTimeout", "100"));
        this.timeoutSocket = Integer.valueOf(props.getProperty("MailSocketIOTimeout", "100"));
        String username = props.getProperty("SmtpUsername", "");
        String password = props.getProperty("SmtpPassword", "");
        String recipientSource = props.getProperty("RecipientSource", "");

        if (StringHelper.isNonEmpty(username)) {
            this.authenticator = new EmailAuthenticator(username, password);
        } else {
            this.authenticator = null;
        }

        this.serverProps = new Properties();
        this.serverProps.put("mail.smtp.host", this.serverAddress);
        this.serverProps.put("mail.smtp.port", this.smtpPort);
        this.serverProps.put("mail.smtp.connectiontimeout", 1000L * this.timeoutProtoInit);
        this.serverProps.put("mail.smtp.timeout", 1000L * this.timeoutSocket);
        if (this.authenticator != null) {
            this.serverProps.put("mail.smtp.auth", "true");
        }
        this.serverProps.put("mail.smtp.recipientsource", recipientSource);
    }

    protected Session getSession() {
        return Session.getInstance(getProperties(), this.authenticator);
    }

    protected Properties getProperties() {
        return this.serverProps;
    }

    public String getServerAddress() {
        return this.serverAddress;
    }


    public String toString() {
        return "MailServer [" + this.serverAddress + "]";
    }
}
