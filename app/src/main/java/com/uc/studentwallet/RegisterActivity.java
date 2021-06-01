package com.uc.studentwallet;

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


public class RegisterActivity extends AppCompatActivity {

    Intent intent;
    TextView register_login_text;
    TextInputLayout register_input_username, register_input_password;
    Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponent();
        loginExistingUserBtn();

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = register_input_username.getEditText().getText().toString().trim();
                String password = register_input_password.getEditText().getText().toString().trim();
                User newUser = new User(username, password);
                register(newUser);
            }
        });
    }

    private void initComponent() {
        register_login_text = findViewById(R.id.register_login_text);
        register_input_username = findViewById(R.id.register_input_username);
        register_input_password = findViewById(R.id.register_input_password);
        register_btn = findViewById(R.id.register_btn);
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
        String url = "http://192.168.0.176/student_wallet/register.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getBaseContext(), "Success!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Failed!" + error, Toast.LENGTH_SHORT).show();
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