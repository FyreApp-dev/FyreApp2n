package com.msgr2.fyreapp.view.startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.msgr2.fyreapp.MainActivity;
import com.msgr2.fyreapp.R;
import com.msgr2.fyreapp.view.auth.PhoneAuthActivity;

public class WelcomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Button button = findViewById(R.id.btn_continue_1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreenActivity.this, PhoneAuthActivity.class));
            }
        });
    }
}