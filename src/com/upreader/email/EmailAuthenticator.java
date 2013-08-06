package com.upreader.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmailAuthenticator extends Authenticator {
    private final PasswordAuthentication pauth;

    public EmailAuthenticator(String username, String password) {
        this.pauth = new PasswordAuthentication(username, password);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return this.pauth;
    }
}
