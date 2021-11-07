package com.msgr2.fyreapp.service;

import com.msgr2.fyreapp.service.user.UserUploadService;

public class Service {
    public static UserUploadService upload(){
        return new UserUploadService();
    }
}
