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

public class WithdrawActivity extends AppCompatActivity {

    private TextInputLayout withdraw_input_nominal;
    private Button withdraw_btn;
    private TextView option1, option2, option3, user_balance;
    private ImageView withdraw_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        initComponent();

        user_balance.setText(HomeFragment.formatter((double) MainActivity.balance));

        withdraw_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdraw_input_nominal.getEditText().setText("25000");
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdraw_input_nominal.getEditText().setText("50000");
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdraw_input_nominal.getEditText().setText("100000");
            }
        });

        withdraw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nominal = Integer.parseInt(withdraw_input_nominal.getEditText().getText().toString().trim());
                if(MainActivity.balance - 10000 > nominal) {
                    withdraw(nominal);
                }
            }
        });
    }

    public void initComponent(){
        withdraw_input_nominal = findViewById(R.id.withdraw_input_nominal);
        withdraw_btn = findViewById(R.id.withdraw_btn);
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        withdraw_back_btn = findViewById(R.id.withdraw_back_btn);
        user_balance = findViewById(R.id.user_balance);
    }

    public void withdraw(int nominal){
        String url = "https://student.hackerexperience.net/withdraw.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("success")) {
                    Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
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
                data.put("id", String.valueOf(MainActivity.id));
                data.put("nominal", String.valueOf(nominal));

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}