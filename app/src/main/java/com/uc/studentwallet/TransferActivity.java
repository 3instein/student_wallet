package com.uc.studentwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import static com.uc.studentwallet.HomeFragment.formatter;

public class TransferActivity extends AppCompatActivity {

    private Button transfer_btn;
    private TextInputLayout transfer_input_target, transfer_input_nominal;
    private TextView option1, option2, option3, user_balance;
    private ImageView transfer_back_btn;
    private int transfer_nominal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        initComponent();

        transfer_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        transfer_input_target.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String username = transfer_input_target.getEditText().getText().toString().trim();

                if (username.isEmpty()) {
                    transfer_input_target.setError("Student ID cannot be empty!");
                    transfer_btn.setEnabled(false);
                } else {
                    transfer_input_target.setError("");
                    transfer_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        transfer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String target = transfer_input_target.getEditText().getText().toString().trim();
                transfer_nominal = Integer.parseInt(transfer_input_nominal.getEditText().getText().toString().trim());
                transfer(target, String.valueOf(transfer_nominal), MainActivity.id);
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
        transfer_input_nominal = findViewById(R.id.transfer_input_nominal);
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        transfer_btn = findViewById(R.id.transfer_btn);
        transfer_back_btn = findViewById(R.id.transfer_back_btn);
        user_balance = findViewById(R.id.user_balance);
        transfer_btn.setEnabled(false);
        user_balance.setText(String.valueOf(formatter((double) MainActivity.balance)));
    }

    private void transfer(String target, String transfer_nominal, int id) {
        String url = "https://student.hackerexperience.net/transfer.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("success")) {
                    Toast.makeText(getBaseContext(), "Transfer Success", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (response.equalsIgnoreCase("invalid nim")) {
                    Toast.makeText(getBaseContext(), "Invalid Student ID", Toast.LENGTH_SHORT).show();
                } else if(response.equalsIgnoreCase("balance")){
                    Toast.makeText(getBaseContext(), "Not enough balance", Toast.LENGTH_SHORT).show();
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