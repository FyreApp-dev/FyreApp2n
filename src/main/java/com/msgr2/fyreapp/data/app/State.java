package com.msgr2.fyreapp.data.app;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestoreSettings;


public class State {
    @NonNull
    public static State getInstance(){
        return new State();
    }

    public boolean userAccount(){
        boolean state;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            state = true;
        } else {
            state = false;
        }

        return state;
    }



    public FirebaseUser getCurrentUser(){
        FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        return userId;
    }

    public String getCurrentUserId(){
        FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        String id = userId.getUid();
        return id;
    }

    public FirebaseFirestoreSettings diskPersistenceState() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        return settings;
    }
}

