package com.uc.studentwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

public class TopUpActivity extends AppCompatActivity {

    private Intent intent;
    private TextView user_balance, option_1, option_2, option_3;
    private TextInputLayout topUp_input_nominal;
    private Button topUp_btn;
    private ImageView topUp_back_btn;
    private int topUp_nominal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        initComponent();

        intent = getIntent();
        user_balance.setText(HomeFragment.formatter((double) intent.getIntExtra("balance", 0)));

        topUp_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        topUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topUp_nominal = Integer.parseInt(topUp_input_nominal.getEditText().getText().toString().trim());

                if (MainActivity.balance - 10000 > topUp_nominal) {
                    topUp(String.valueOf(topUp_nominal));
                    MainActivity.balance += topUp_nominal;
                }
            }
        });

        option_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topUp_input_nominal.getEditText().setText("25000");
            }
        });

        option_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topUp_input_nominal.getEditText().setText("50000");
            }
        });

        option_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topUp_input_nominal.getEditText().setText("100000");
            }
        });
    }

    private void initComponent() {
        user_balance = findViewById(R.id.user_balance);
        option_1 = findViewById(R.id.option_1);
        option_2 = findViewById(R.id.option_2);
        option_3 = findViewById(R.id.option_3);
        topUp_input_nominal = findViewById(R.id.topUp_input_nominal);
        topUp_btn = findViewById(R.id.topUp_btn);
        topUp_back_btn = findViewById(R.id.topUp_back_btn);
    }

    private void topUp(String topUp_nominal) {
        String url = "https://student.hackerexperience.net/top_up.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("success")) {
                    Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();
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
                data.put("topUp_nominal", topUp_nominal);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}