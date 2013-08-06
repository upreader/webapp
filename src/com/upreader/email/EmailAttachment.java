package com.upreader.email;

import java.io.File;
import java.io.InputStream;

public class EmailAttachment {
    private File file = null;
    private String name = null;
    private Object object = null;

    public EmailAttachment(File file, String name) {
        setFile(file);
        setName(name);
    }

    public EmailAttachment(File file) {
        setFile(file);
        setName(file.getName());
    }

    public EmailAttachment(Object obj, String name) {
        this.object = obj;
        setName(name);
    }

    public Object getObjectAttachment() {
        return this.object;
    }

    public InputStream getInputStream() {
        if ((this.object instanceof InputStream)) {
            return (InputStream) this.object;
        }

        return null;
    }

    public void setFile(File attachment) {
        if ((attachment != null) && (attachment.isFile())) {
            this.file = attachment;
        }
    }

    public File getFile() {
        return this.file;
    }

    protected void setName(String attachmentName) {
        this.name = attachmentName;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "EmailAttachment [file name:" + getFile().getName() +
                "; attachment name:" + getName() +
                "]";
    }
}
