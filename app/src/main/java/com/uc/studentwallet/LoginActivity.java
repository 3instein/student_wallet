package com.uc.studentwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Intent intent;
    TextView login_signup_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponent();
        createNewAccountBtn();
    }

    private void initComponent() {
        login_signup_text = (TextView) findViewById(R.id.login_signup_text);
    }

    private void createNewAccountBtn() {
        login_signup_text.setText(Html.fromHtml("<font color='#808080'>Don't have an account? </font><font color='#000'>Sign Up.</font>"));

        login_signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}