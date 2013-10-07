package com.upreader.dto;

/**
 * Created
 * User: Razvan.Ionescu
 * Date: 10/5/13
 */
public class CrocodocResultDTO {
    private boolean result;
    private String docStatus;
    private String sessionKey;

    public CrocodocResultDTO(boolean result, String docStatus, String sessionKey) {
        this.result = result;
        this.docStatus = docStatus;
        this.sessionKey = sessionKey;
    }

    public CrocodocResultDTO() {
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
