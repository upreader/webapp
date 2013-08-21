package com.upreader.dto;

import java.util.Date;

/**
 * Created
 * User: Razvan.Ionescu
 * Date: 8/21/13
 */
public class AmazonS3FileDetailsBuilder{
    protected String key;
    protected String link;
    protected String ext;
    protected String fileName;
    protected String size;
    protected String folder;
    protected Date lastModified;

    public AmazonS3FileDetailsBuilder withKey(String key){
        this.key = key;
        return this;
    }

    public AmazonS3FileDetailsBuilder withLink(String link){
        this.link = link;
        return this;
    }

    public AmazonS3FileDetailsBuilder withExt(String ext){
        this.ext = ext;
        return this;
    }

    public AmazonS3FileDetailsBuilder withFileName(String fileName){
        this.fileName = fileName;
        return this;
    }

    public AmazonS3FileDetailsBuilder withSize(String size){
        this.size = size;
        return this;
    }

    public AmazonS3FileDetailsBuilder withFolder(String folder){
        this.folder = folder;
        return this;
    }

    public AmazonS3FileDetailsBuilder withLastModified(Date lastModified){
        this.lastModified = lastModified;
        return this;
    }

    public AmazonS3FileDetails build(){
        return new AmazonS3FileDetails(this);
    }
}
