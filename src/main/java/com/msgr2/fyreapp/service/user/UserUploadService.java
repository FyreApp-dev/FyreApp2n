package com.msgr2.fyreapp.service.user;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.msgr2.fyreapp.data.app.FyreData;
import com.msgr2.fyreapp.data.app.State;
import com.msgr2.fyreapp.view.profile.ProfileActivity;

import java.util.HashMap;

public class UserUploadService {


    public UserUploadService(){}

    protected String getFileExtention(Uri uri, Context context){
        ContentResolver contentResolver = context.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void profilePhoto(Uri imageUri, Context context, ProgressDialog progressDialog, Intent intent){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        final String uId = State.getInstance().getCurrentUserId();


        if(imageUri != null){

            progressDialog.setMessage("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StorageReference reference = FirebaseStorage.getInstance().getReference()
                    .child(FyreData.USER_INFO_PROFILE_PHOTO_ABS_PATH + "/" + System.currentTimeMillis() + "." + getFileExtention(imageUri, context));
            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();
                    final String download_url = String.valueOf(downloadUrl);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("userPhoto", download_url);

                    firestore.collection(FyreData.USER_ABS_PATH).document(uId).update(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //ProfileActivity.getInstance().restart();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

    }
}
