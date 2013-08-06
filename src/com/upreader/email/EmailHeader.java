package com.upreader.email;

public class EmailHeader {
    private String headerName;
    private String headerValue;

    public EmailHeader(String name, String value) {
        this.headerName = name;
        this.headerValue = value;
    }

    public String getHeaderName() {
        return this.headerName;
    }

    public void setHeaderName(String name) {
        this.headerName = name;
    }

    public String getHeaderValue() {
        return this.headerValue;
    }

    public void setHeaderValue(String value) {
        this.headerValue = value;
    }
}
