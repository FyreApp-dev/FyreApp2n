package com.msgr2.fyreapp.view.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;
import com.msgr2.fyreapp.R;
import com.msgr2.fyreapp.data.app.FyreData;
import com.msgr2.fyreapp.data.app.State;
import com.msgr2.fyreapp.databinding.ActivityProfileBinding;
import com.msgr2.fyreapp.others.PhoneNumberFormatCls;
import com.msgr2.fyreapp.service.Service;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private FirebaseFirestore firestore;
    private FirebaseUser firebaseUser;
    private BottomSheetDialog bottomSheetDialog;
    private String uId;
    private CountryCodePicker format;
    private ProgressDialog progressDialog;

    private int IMAGE_GALLERY_REQUEST = 111;
    private Uri imageUri;

    public static ProfileActivity getInstance() {

        return new ProfileActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        setSupportActionBar(binding.mtoolbarProfile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        firebaseUser = State.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        if(firebaseUser != null){
            uId = State.getInstance().getCurrentUserId();
            getUserInfo();
        }

        initActionClick();



    }

    private void initActionClick() {
        binding.fabAddDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomPickPhoto();
            }
        });
    }

    private void showBottomPickPhoto() {
        @SuppressWarnings("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_pick, null);
        ((View) view.findViewById(R.id.ll_pick_gall)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                bottomSheetDialog.dismiss();
            }
        });

        ((View) view.findViewById(R.id.ll_pick_cam)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dispatchImagePickerIntent();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select image"), IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode > 0){
            imageUri = data.getData();
            Intent i = getIntent();
            Service.upload().profilePhoto(imageUri, ProfileActivity.this, progressDialog, i);
            getUserInfo();
        }
    }

    private void dispatchImagePickerIntent() {


    }

    private void getUserInfo() {
        firestore.collection(FyreData.USER_ABS_PATH).document(uId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //ModelUsers user = documentSnapshot.get(ModelUsers.class);
                final String userName = documentSnapshot.getString("userName");
                final String userPhone = documentSnapshot.getString("userPhone");
                final String imageProfile = documentSnapshot.getString("userPhoto");

                String phone = PhoneNumberFormatCls.getInstance().simpleFormat(userPhone);
                binding.tvProfileName.setText(userName);
                binding.tvProfilePhone.setText(phone);
                if(imageProfile != null){
                    Glide.with(ProfileActivity.this).load(imageProfile).into(binding.ivDp);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void restart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}