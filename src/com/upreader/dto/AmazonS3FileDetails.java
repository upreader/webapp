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
    private boolean itIsImage;
    private boolean itIsPublic;
    private Date lastModified;
    protected String crocoUUID;

    public AmazonS3FileDetails(){}

    public AmazonS3FileDetails(AmazonS3FileDetailsBuilder builder){
        this.key      = builder.key;
        this.link     = builder.link;
        this.ext      = builder.ext;
        this.fileName = builder.fileName;
        this.size     = builder.size;
        this.folder   = builder.folder;
        this.itIsImage    = builder.itIsImage;
        this.itIsPublic   = builder.itIsPublic;
        this.lastModified = builder.lastModified;
        this.crocoUUID = builder.crocoUUID;
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

    public boolean isItIsImage() {
        return itIsImage;
    }

    public boolean isItIsPublic() {
        return itIsPublic;
    }

    public Date getLastModified(){
        return lastModified;
    }

    public String getCrocoUUID() {
        return crocoUUID;
    }
}
