package com.upreader.dto;

import java.util.Date;

/**
 * Created
 * User: Razvan.Ionescu
 * Date: 8/21/13
 */
public class AmazonS3FileDetails {
    private String key;
    private String link;
    private String ext;
    private String fileName;
    private String size;
    private String folder;
    private Date lastModified;

    public AmazonS3FileDetails(AmazonS3FileDetailsBuilder builder){
        this.key      = builder.key;
        this.link     = builder.link;
        this.ext      = builder.ext;
        this.fileName = builder.fileName;
        this.size     = builder.size;
        this.folder   = builder.folder;
        this.lastModified     = builder.lastModified;
    }

    public String getKey() {
        return key;
    }

    public String getLink() {
        return link;
    }

    public String getExt() {
        return ext;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSize() {
        return size;
    }

    public String getFolder() {
        return folder;
    }

    public Date getLastModified(){
        return lastModified;
    }
}
