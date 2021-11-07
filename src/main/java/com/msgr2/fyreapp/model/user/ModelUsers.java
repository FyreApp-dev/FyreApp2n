package com.msgr2.fyreapp.model.user;

public class ModelUsers {
    private String userID;
    private String userName;
    private String userPhone;
    private String userPhoto;
    private String userBio;

    public ModelUsers(){}
    public ModelUsers(String userID, String userName, String userPhone, String userPhoto, String userBio) {
        this.userID = userID;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userPhoto = userPhoto;
        this.userBio = userBio;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }
}
