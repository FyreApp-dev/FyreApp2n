package com.msgr2.fyreapp.view.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.msgr2.fyreapp.MainActivity;
import com.msgr2.fyreapp.R;
import com.msgr2.fyreapp.data.app.FyreData;
import com.msgr2.fyreapp.databinding.ActivityProfileSetupBinding;
import com.msgr2.fyreapp.model.user.ModelUsers;

public class ProfileSetupActivity extends AppCompatActivity {

    private ActivityProfileSetupBinding binding;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_setup);

        progressDialog = new ProgressDialog(this);
        initBtnClick();
    }

    private void initBtnClick() {
        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

        binding.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
    }

    private void pickImage() {
        //  TO-DO Later
    }

    private void updateUserProfile() {
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            final String uId = firebaseUser.getUid();
            final String userPhone = firebaseUser.getPhoneNumber();
            final String userName = binding.edtName.getText().toString();
             ModelUsers users = new ModelUsers(
                    uId,
                    userName,
                    userPhone,
                    "",
                    ""
            );

            firestore.collection(FyreData.USER_ABS_PATH).document(uId).set(users)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileSetupActivity.this, "updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ProfileSetupActivity.this, MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(ProfileSetupActivity.this, "error", Toast.LENGTH_SHORT).show();

                }
            });


        } else {
            progressDialog.dismiss();
            Toast.makeText(ProfileSetupActivity.this, "error", Toast.LENGTH_SHORT).show();
        }


    }
}