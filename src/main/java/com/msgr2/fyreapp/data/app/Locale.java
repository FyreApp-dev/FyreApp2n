package com.msgr2.fyreapp.data.app;

import android.content.Context;

import androidx.annotation.NonNull;

public class Locale {
    @NonNull
    public static Locale getInstance(){
        return new Locale();
    }

    public String getISO3Country(Context context){
        return context.getResources().getConfiguration().locale.getISO3Country();
    }
}
