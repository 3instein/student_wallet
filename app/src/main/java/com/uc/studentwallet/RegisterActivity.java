package com.uc.studentwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    Intent intent;
    TextView register_login_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponent();
        loginExistingUserBtn();
    }

    private void initComponent() {
        register_login_text = (TextView) findViewById(R.id.register_login_text);
    }

    private void loginExistingUserBtn() {
        register_login_text.setText(Html.fromHtml("<font color='#808080'>Already have an account? </font><font color='#000'>Login.</font>"));

        register_login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}