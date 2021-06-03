package com.uc.studentwallet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import model.User;

public class LoginActivity extends AppCompatActivity {

    Intent intent;
    TextView login_signup_text;
    TextInputLayout login_input_username, login_input_password;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponent();
        createNewAccountBtn();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = login_input_username.getEditText().getText().toString().trim();
                String password = login_input_password.getEditText().getText().toString().trim();
                User existingUser = new User(username, password);
                login(existingUser);
            }
        });
    }

    private void initComponent() {
        login_signup_text = findViewById(R.id.login_signup_text);
        login_input_username = findViewById(R.id.login_input_username);
        login_input_password = findViewById(R.id.login_input_password);
        login_btn = findViewById(R.id.login_btn);
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

    private void login(User user) {
        String url = "http://student.hackerexperience.net/login.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();

                if (response.equalsIgnoreCase("Login success!")) {
                    intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("username", user.getUsername());
                data.put("password", user.getPassword());

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}