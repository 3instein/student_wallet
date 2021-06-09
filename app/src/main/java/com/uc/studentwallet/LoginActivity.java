package com.uc.studentwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import model.User;

public class LoginActivity extends AppCompatActivity {

    private Intent intent;
    private TextView login_signup_text, login_forgot_password;
    private TextInputLayout login_input_username, login_input_password;
    private Button login_btn;
    private CheckBox stay_logged_in_checker;
    int stay_logged_in = 0;

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

        login_input_username.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String username = login_input_username.getEditText().getText().toString().trim();

                if (username.isEmpty()) {
                    login_input_username.setError("Username cannot be empty!");
                    login_btn.setEnabled(false);
                } else {
                    login_input_username.setError("");
                    login_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login_input_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = login_input_password.getEditText().getText().toString().trim();

                if (password.isEmpty()) {
                    login_input_password.setError("Password cannot be empty!");
                    login_btn.setEnabled(false);
                } else {
                    login_input_password.setError("");
                    login_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        stay_logged_in_checker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(((CompoundButton) buttonView).isChecked()){
                    stay_logged_in = 1;
                } else {
                    stay_logged_in = 0;
                }
            }
        });

        login_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), ForgotActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initComponent() {
        login_signup_text = findViewById(R.id.login_signup_text);
        login_input_username = findViewById(R.id.login_input_username);
        login_input_password = findViewById(R.id.login_input_password);
        login_btn = findViewById(R.id.login_btn);
        stay_logged_in_checker = findViewById(R.id.stay_logged_in_checker);
        login_forgot_password = findViewById(R.id.login_forgot_password);
    }

    private void createNewAccountBtn() {
        login_signup_text.setText(Html.fromHtml("<font color='#808080'>Don't have an account? </font><font color='#000'>Sign Up.</font>"));

        login_signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(User user) {
        String url = "https://student.hackerexperience.net/login.php";

        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", user.getUsername());
            jsonObject.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject userDetail = response.getJSONObject("userDetail");
                    String message = userDetail.getString("message");
                    if (message.equals("success")) {
                        intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("id", userDetail.getInt("id"));
                        intent.putExtra("balance", userDetail.getInt("balance"));
                        intent.putExtra("nim", userDetail.getInt("nim"));
                        intent.putExtra("full_name", userDetail.getString("full_name"));
                        intent.putExtra("username", userDetail.getString("username"));
                        if (stay_logged_in == 1) {
                            String username = user.getUsername();
                            String password = user.getPassword();
                            SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                            SharedPreferences.Editor Ed = sp.edit();
                            Ed.putString("username", username);
                            Ed.putString("password", password);
                            Ed.commit();
                        }

                        startActivity(intent);
                        finish();
                    } else if (message.equals("invalid")) {
                        Toast.makeText(getBaseContext(), "Wrong Username / Password!", Toast.LENGTH_SHORT).show();
                    } else if (message.equals("no data")) {
                        Toast.makeText(getBaseContext(), "Account not found!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}