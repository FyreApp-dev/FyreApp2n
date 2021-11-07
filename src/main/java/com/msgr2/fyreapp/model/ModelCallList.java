package com.msgr2.fyreapp.model;

public class ModelCallList {
    private String contactID;
    private String contactName;
    private String callTime;
    private String urlContactPhoto;
    private String callType;

    public ModelCallList() {
    }

    public ModelCallList(String contactID, String contactName, String callTime, String urlContactPhoto, String callType) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.callTime = callTime;
        this.urlContactPhoto = urlContactPhoto;
        this.callType = callType;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getUrlContactPhoto() {
        return urlContactPhoto;
    }

    public void setUrlContactPhoto(String urlContactPhoto) {
        this.urlContactPhoto = urlContactPhoto;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }
}
