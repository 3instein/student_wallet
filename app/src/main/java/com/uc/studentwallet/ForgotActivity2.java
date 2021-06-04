package com.uc.studentwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class ForgotActivity2 extends AppCompatActivity {

    private TextInputLayout forgot2_token, forgot2_new_password;
    private Button forgot2_back, forgot2_reset_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot2);
    }

    private void initComponent() {
        forgot2_token = findViewById(R.id.forgot2_token);
        forgot2_new_password = findViewById(R.id.forgot2_new_password);
        forgot2_back = findViewById(R.id.forgot2_back);
        forgot2_reset_password = findViewById(R.id.forgot2_reset_password);
    }
}