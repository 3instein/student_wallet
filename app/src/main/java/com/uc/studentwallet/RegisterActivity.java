package com.uc.studentwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
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
import java.util.regex.Pattern;

import model.User;


public class RegisterActivity extends AppCompatActivity {

    Intent intent;
    TextView register_login_text;
    TextInputLayout register_input_nim, register_input_fullname, register_input_email, register_input_username, register_input_password;
    Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponent();
        loginExistingUserBtn();
        initFormValidation();

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = register_input_nim.getEditText().getText().toString().trim();
                String full_name = register_input_fullname.getEditText().getText().toString().trim();
                String email = register_input_email.getEditText().getText().toString().trim();
                String username = register_input_username.getEditText().getText().toString().trim();
                String password = register_input_password.getEditText().getText().toString().trim();
                User newUser = new User(nim, full_name, username, email, password);
                register(newUser);
            }
        });


    }

    private void initComponent() {
        register_input_nim = findViewById(R.id.register_input_nim);
        register_input_fullname = findViewById(R.id.register_input_fullname);
        register_input_username = findViewById(R.id.register_input_username);
        register_input_password = findViewById(R.id.register_input_password);
        register_input_email = findViewById(R.id.register_input_email);
        register_login_text = findViewById(R.id.register_login_text);
        register_btn = findViewById(R.id.register_btn);
    }

    private void initFormValidation(){
        register_input_nim.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nim = register_input_nim.getEditText().getText().toString().trim();

                if(nim.isEmpty()){
                    register_input_nim.setError("Password cannot be empty!");
                    register_btn.setEnabled(false);
                }else{
                    register_input_nim.setError("");
                    register_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        register_input_fullname.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String full_name = register_input_nim.getEditText().getText().toString().trim();

                if(full_name.isEmpty()){
                    register_input_fullname.setError("Password cannot be empty!");
                    register_btn.setEnabled(false);
                }else{
                    register_input_fullname.setError("");
                    register_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        register_input_username.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String username = register_input_username.getEditText().getText().toString().trim();

                if(username.isEmpty()){
                    register_input_username.setError("Password cannot be empty!");
                    register_btn.setEnabled(false);
                }else{
                    register_input_username.setError("");
                    register_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        register_input_email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = register_input_email.getEditText().getText().toString().trim();

                Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");

                if(email.isEmpty()){
                    register_input_email.setError("Email cannot be empty!");
                    register_btn.setEnabled(false);
                }else{
                    if (!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
                        register_input_email.setError("Wrong email format");
                        register_btn.setEnabled(false);
                    }else{
                        register_input_email.setError("");
                        register_btn.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        register_input_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = register_input_password.getEditText().getText().toString().trim();

                Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{0,20}");

                if (password.isEmpty()){
                    register_input_password.setError("Password cannot be empty!");
                    register_btn.setEnabled(false);
                }else{
                    if (password.length() < 8 || password.length() > 20){
                        register_input_password.setError("Password must be 8 to 20 characters");
                        register_btn.setEnabled(false);
                    }else if (!PASSWORD_PATTERN.matcher(password).matches()){
                        register_input_password.setError("Must contain a - z, A - Z, !, @, #, $");
                        register_btn.setEnabled(false);
                    }else{
                        register_input_password.setError("");
                        register_btn.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    private void register(User user) {
        String url = "http://student.hackerexperience.net/register.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Registered!")) {
                    Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();

                    intent = new Intent(getBaseContext(), LoginActivity.class);
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
                data.put("nim", String.valueOf(user.getNim()));
                data.put("full_name", user.getFull_name());
                data.put("username", user.getUsername());
                data.put("email", user.getEmail());
                data.put("password", user.getPassword());

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}