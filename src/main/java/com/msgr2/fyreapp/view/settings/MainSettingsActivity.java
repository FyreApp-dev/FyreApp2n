package com.msgr2.fyreapp.view.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.msgr2.fyreapp.R;
import com.msgr2.fyreapp.data.app.FyreData;
import com.msgr2.fyreapp.data.app.State;
import com.msgr2.fyreapp.databinding.ActivityMainSettingsBinding;
import com.msgr2.fyreapp.view.profile.ProfileActivity;

import java.util.Objects;

public class MainSettingsActivity extends AppCompatActivity {

    private ActivityMainSettingsBinding binding;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firestore;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_settings);


        firestore = FirebaseFirestore.getInstance();
        firebaseUser = State.getInstance().getCurrentUser();
        initClickAction();
        if (firebaseUser != null) {
            getUserInfo();
        }

    }

    private void initClickAction() {
        binding.llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainSettingsActivity.this, ProfileActivity.class));
            }
        });
    }

    private void getUserInfo() {
        firestore.collection(FyreData.USER_ABS_PATH).document(State.getInstance().getCurrentUserId()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        // ModelUsers info = documentSnapshot.
                        final String userName = Objects.requireNonNull(documentSnapshot.get("userName")).toString();
                        final String userPhoto = Objects.requireNonNull(documentSnapshot.get("userPhoto")).toString();

                        if(userPhoto != null){
                            Glide.with(MainSettingsActivity.this).load(userPhoto).into(binding.ivSettingsDp);
                        }
                        binding.tvSettingContactName.setText(userName);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}