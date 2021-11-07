package com.msgr2.fyreapp.view.auth;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;
import com.msgr2.fyreapp.R;
import com.msgr2.fyreapp.data.app.State;
import com.msgr2.fyreapp.databinding.ActivityPhoneAuthBinding;
import com.msgr2.fyreapp.others.PhoneNumberFormatCls;

import java.util.concurrent.TimeUnit;

public class PhoneAuthActivity extends AppCompatActivity {

    private static final String TAG = "PhoneAuthActivity";
    private ActivityPhoneAuthBinding binding;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firestore;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks changedCallbacks;
    private boolean verificationInProgress;
    private CountryCodePicker countryCodePicker;
    private String verificationId;
    private String otpCode;
    private String cc;
    private String num;
    private boolean phoneIsValid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_auth);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        if (State.getInstance().userAccount() == true) {
            startActivity(new Intent(PhoneAuthActivity.this, ProfileSetupActivity.class));
        }

        countryCodePicker = binding.ccPicker;
        countryCodePicker.registerCarrierNumberEditText(binding.edtAuthPhoneNumber);
        countryCodePicker.setDialogEventsListener(new CountryCodePicker.DialogEventsListener() {
            @Override
            public void onCcpDialogOpen(Dialog dialog) {

            }

            @Override
            public void onCcpDialogDismiss(DialogInterface dialogInterface) {

            }

            @Override
            public void onCcpDialogCancel(DialogInterface dialogInterface) {

            }
        });
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                binding.edtAuthPhoneCountryCode.setText(Integer.toString(countryCodePicker.getSelectedCountryCodeAsInt()));
                binding.edtAuthPhoneNumber.requestFocus();
            }
        });
        binding.edtAuthPhoneCountryCode.setText(Integer.toString(countryCodePicker.getSelectedCountryCodeAsInt()));

        binding.edtAuthPhoneCountryCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int len = binding.edtAuthPhoneCountryCode.getText().length();
                if (len == 2) {
                    binding.edtAuthPhoneNumber.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.btnAuthNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryCodePicker.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
                    @Override
                    public void onValidityChanged(boolean isValidNumber) {
                        if (isValidNumber) {
                            phoneIsValid = true;
                        } else {
                            if((binding.edtAuthPhoneNumber.getText().toString() != null) || (binding.edtAuthPhoneNumber.getText().toString() != null)) {
                                Toast.makeText(PhoneAuthActivity.this, "Invalid phone number format", Toast.LENGTH_SHORT).show();
                                phoneIsValid = false;
                            }
                        }
                    }
                });
                if (phoneIsValid) {
                    num = countryCodePicker.getFullNumberWithPlus();
                    String num2 = countryCodePicker.getFormattedFullNumber();
                    if (binding.btnAuthNext1.getText().toString().equals("Next")) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PhoneAuthActivity.this);
                        alertDialogBuilder.setTitle("Confirm phone number");
                        alertDialogBuilder.setIcon(R.drawable.ic_baseline_smartphone_24);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            alertDialogBuilder.setMessage("We will send a verification code to \n\n"
                                    + HtmlCompat.fromHtml("<b>"+ num2 +"</b>", HtmlCompat.FROM_HTML_MODE_LEGACY) +
                                    "\n\n" + "Are you sure this is correct ?"
                            );
                        } else {
                            alertDialogBuilder.setMessage("We will send a verification code to \n\n" +
                                    "+" + cc + " " + PhoneNumberFormatCls.getInstance().format(num) + "\n\n" +
                                    "Are you sure this is correct ?"
                            );
                        }
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                progressDialog.setMessage("Please wait");
                                progressDialog.show();
                                progressDialog.setCancelable(false);
                                startPhoneNumberVerification(countryCodePicker.getFullNumberWithPlus());
                            }
                        });

                        alertDialogBuilder.setNeutralButton("Not sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(PhoneAuthActivity.this, "You clicked on Not sure", Toast.LENGTH_SHORT).show();
                            }
                        });

                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(PhoneAuthActivity.this, "You clicked over No", Toast.LENGTH_SHORT).show();
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        progressDialog.setMessage("Verifying " + countryCodePicker.getFormattedFullNumber());
                        progressDialog.show();
                        verifyPhoneNumberWithCode(verificationId, otpCode);
                    }
                } else {
                    if(countryCodePicker.getFullNumberWithPlus().isEmpty()){
                        Toast.makeText(PhoneAuthActivity.this, "Phone number can not be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        changedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "signInWithCredential:success");

                //Getting the code sent by SMS
                String code = phoneAuthCredential.getSmsCode();
                //sometime the code is not detected automatically
                //in this case the code will be null
                //so user has to manually enter the code
                if (code != null) {
                    saveCode(code);
                    binding.edAuthCode.setText(code);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d(TAG, "signInWithCredential:fail" + e.getMessage());
                Toast.makeText(PhoneAuthActivity.this, "Unable to send verification code to " + countryCodePicker.getFormattedFullNumber(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onCodeSent(@NonNull String verificationID,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "code sent" + verificationID);
                Toast.makeText(PhoneAuthActivity.this, "Code sent to " + countryCodePicker.getFullNumberWithPlus(), Toast.LENGTH_SHORT).show();
                verificationId = verificationID;
                resendingToken = token;
                binding.edtAuthPhoneCountryCode.setFocusable(false);
                binding.edtAuthPhoneNumber.setFocusable(false);

                binding.edAuthCode.setFocusable(true);
                binding.edAuthCode.requestFocus();

                binding.btnAuthNext1.setText("Continue");
                progressDialog.dismiss();
            }
        };

    }

    private void saveCode(String code) {
        otpCode = code;
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(changedCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        verificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            progressDialog.dismiss();
                            FirebaseUser user = task.getResult().getUser();
                            /*if(user != null){
                                final String userID = user.getUid();
                                final String userPhone = user.getPhoneNumber();
                                ModelUsers users = new ModelUsers(
                                        userID,
                                        "",
                                        userPhone,
                                        "",
                                        ""
                                );

                                firestore.collection(FyreData.USER_ABS_PATH).document(FyreData.USER_INFO_ABS_PATH).collection(userID)
                                        .add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        startActivity(new Intent(PhoneAuthActivity.this, ProfileSetupActivity.class));
                                    }
                                });
                            } else {
                                Toast.makeText(PhoneAuthActivity.this, "Something went wrong.!", Toast.LENGTH_SHORT).show();
                            }*/
                            startActivity(new Intent(PhoneAuthActivity.this, ProfileSetupActivity.class));

                        } else {
                            // Sign in failed, display a message and update the UI
                            progressDialog.dismiss();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                            }
                        }
                    }
                });
    }
}