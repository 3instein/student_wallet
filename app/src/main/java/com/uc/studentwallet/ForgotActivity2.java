package com.uc.studentwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class ForgotActivity2 extends AppCompatActivity {

    private TextInputLayout forgot2_token, forgot2_new_password;
    private Button forgot2_back, forgot2_reset_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot2);
        initComponent();

        forgot2_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ForgotActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forgot2_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = forgot2_token.getEditText().getText().toString().trim();
                String new_password = forgot2_new_password.getEditText().getText().toString().trim();

                resetPassword(token, new_password);
                finish();
            }
        });
    }

    private void initComponent() {
        forgot2_token = findViewById(R.id.forgot2_token);
        forgot2_new_password = findViewById(R.id.forgot2_new_password);
        forgot2_back = findViewById(R.id.forgot2_back);
        forgot2_reset_password = findViewById(R.id.forgot2_reset_password);
    }

    public void resetPassword(String token, String new_password){
        String url = "https://student.hackerexperience.net/forgot.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("success")) {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else if(response.equalsIgnoreCase("invalid")){
                    Toast.makeText(getBaseContext(), "Invalid Token", Toast.LENGTH_SHORT).show();
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
                data.put("token", token);
                data.put("new_password", new_password);

                return data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
}