package com.uc.studentwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Finance;

public class MainActivity extends AppCompatActivity {

    public static int id = 0, balance = 0, nim = 0;
    static String username = "", full_name = "";

    private Intent intent;
    private BottomNavigationView main_bottom_nav;
    private ArrayList<Finance> tempFinance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initValue();
        initComponent();

        Bundle data = new Bundle();
        data.putInt("id", id);
        data.putInt("nim", nim);
        data.putInt("balance", balance);
        data.putString("username", username);
        data.putString("full_name", full_name);

        Fragment fragment = new HomeFragment();
        fragment.setArguments(data);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).commit();

        main_bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Fragment newFragment = null;

                if (item.getItemId() == R.id.nav_home) {
                    newFragment = new HomeFragment();
                    Bundle data = new Bundle();

                    data.putInt("id", id);
                    data.putInt("nim", nim);
                    data.putInt("balance", balance);
                    data.putString("username", username);
                    data.putString("full_name", full_name);
                    newFragment.setArguments(data);
                } else if (item.getItemId() == R.id.nav_finance) {
                    newFragment = new FinanceFragment();
                } else if (item.getItemId() == R.id.nav_profile) {
                    newFragment = new ProfileFragment();
                }
                getBalance();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, newFragment).commit();

                return true;
            }
        });
    }


    private void initValue() {
        intent = getIntent();
        id = intent.getIntExtra("id", 0);
        balance = intent.getIntExtra("balance", 0);
        nim = intent.getIntExtra("nim", 0);
        username = intent.getStringExtra("username");
        full_name = intent.getStringExtra("full_name");
        tempFinance = new ArrayList<>();
    }

    private void initComponent() {
        main_bottom_nav = findViewById(R.id.main_bottom_nav);
    }

    private void getBalance() {
        String url = "https://student.hackerexperience.net/balance.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                balance = Integer.parseInt(response);
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
                data.put("id", String.valueOf(id));

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}