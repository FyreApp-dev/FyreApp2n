package com.msgr2.fyreapp.model;

public class ModelChatList {

    private String contactID;
    private String contactName;
    private String lastMessage;
    private String date;
    private String urlProfilePhoto;

    public ModelChatList(){}
    public ModelChatList(String contactID, String contactName, String lastMessage, String date, String urlProfilePhoto) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.lastMessage = lastMessage;
        this.date = date;
        this.urlProfilePhoto = urlProfilePhoto;
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

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrlProfilePhoto() {
        return urlProfilePhoto;
    }

    public void setUrlProfilePhoto(String urlProfilePhoto) {
        this.urlProfilePhoto = urlProfilePhoto;
    }
}

