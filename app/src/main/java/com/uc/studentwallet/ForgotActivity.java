package com.uc.studentwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class ForgotActivity extends AppCompatActivity {

    private Intent intent;
    private Button forgot1_btn;
    private TextInputLayout forgot_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        initComponent();

        forgot1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), ForgotActivity2.class);
                startActivity(intent);
            }
        });
    }

    private void initComponent() {
        forgot1_btn = findViewById(R.id.forgot1_btn);
        forgot_username = findViewById(R.id.forgot_username);
    }
}