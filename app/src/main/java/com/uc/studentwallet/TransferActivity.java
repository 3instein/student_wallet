package com.uc.studentwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class TransferActivity extends AppCompatActivity {

    private Button transfer_btn;
    private TextInputLayout transfer_input_target, transfer_input_nominal;
    private TextView option1, option2, option3;
    private int transfer_nominal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        initComponent();

        transfer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String target = transfer_input_target.getEditText().getText().toString().trim();
                int transfer_nominal = Integer.parseInt(transfer_input_nominal.getEditText().getText().toString().trim());
                if (MainActivity.balance - 10000 > transfer_nominal) {
                    transfer(target, String.valueOf(transfer_nominal), MainActivity.id);
                } else {
                    Toast.makeText(getBaseContext(), "Not Enough Balance", Toast.LENGTH_SHORT).show();
                }
            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfer_input_nominal.getEditText().setText("25000");
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfer_input_nominal.getEditText().setText("50000");
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfer_input_nominal.getEditText().setText("100000");
            }
        });

    }

    private void initComponent() {
        transfer_input_target = findViewById(R.id.transfer_input_target);
        transfer_input_nominal = findViewById(R.id.withdraw_input_nominal);
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        transfer_btn = findViewById(R.id.transfer_btn);
    }

    private void transfer(String target, String transfer_nominal, int id) {
        String url = "https://student.hackerexperience.net/transfer.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("success")) {
                    Toast.makeText(getBaseContext(), "Transfer Success", Toast.LENGTH_SHORT).show();
                } else if(response.equalsIgnoreCase("invalid nim")){
                    Toast.makeText(getBaseContext(), "Invalid Student ID", Toast.LENGTH_SHORT).show();
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
                data.put("target", target);
                data.put("transfer_nominal", transfer_nominal);
                data.put("id", String.valueOf(id));

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}