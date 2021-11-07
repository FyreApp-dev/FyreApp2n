package com.msgr2.fyreapp.others;

import android.os.Build;
import android.telephony.PhoneNumberUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Locale;

public class PhoneNumberFormatCls {

    public static PhoneNumberFormatCls getInstance(){
        return new PhoneNumberFormatCls();
    }

    public String format(@NonNull String rawPhone) {
        rawPhone = rawPhone.replaceAll(" ", "");
        String phoneFormat = "";
        if (rawPhone.length() > 4) {
            phoneFormat += rawPhone.substring(0, 4);
            rawPhone = rawPhone.substring(4);
        } else {
            return rawPhone;
        }
        phoneFormat += " ";
        if (rawPhone.length() > 3) {
            phoneFormat += rawPhone.substring(0, 3);
            rawPhone = rawPhone.substring(3);
        } else {
            phoneFormat += rawPhone;
            return phoneFormat;
        }

        phoneFormat += " " + rawPhone;

        return phoneFormat;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public String simpleFormat(String userPhone) {
        return PhoneNumberUtils.formatNumber(userPhone, Locale.getDefault().getCountry());
    }
}