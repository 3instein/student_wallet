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

import java.util.HashMap;
import java.util.Map;

import model.Finance;

public class MainActivity extends AppCompatActivity {

    public static int id = 0, balance = 0, nim = 0;
    static String username = "", full_name = "";

    private Intent intent;
    private BottomNavigationView main_bottom_nav;

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
                    getFinance();
                } else if (item.getItemId() == R.id.nav_profile) {

                }
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
    }

    private void initComponent() {
        main_bottom_nav = findViewById(R.id.main_bottom_nav);
    }

    public void getBalance() {
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

    public void getFinance() {
        String url = "https://student.hackerexperience.net/finance.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", MainActivity.id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArrayFinance = response.getJSONArray("userFinance");

                    for (int i = 0; i < jsonArrayFinance.length(); i++) {
                        JSONObject userFinance = jsonArrayFinance.getJSONObject(i);
                        Finance fInance = new Finance();
                        fInance.setFinance_id(userFinance.getInt("id"));
                        fInance.setUser_id(userFinance.getInt("user_id"));
                        fInance.setAmount(userFinance.getInt("amount"));
                        fInance.setStatus(userFinance.getString("status"));

                        FinanceFragment financeFragment = (FinanceFragment) getSupportFragmentManager().getFragments().get(1);
                        financeFragment.addFinanceList(fInance);
                    }
                    FinanceFragment financeFragment = (FinanceFragment) getSupportFragmentManager().getFragments().get(1);
                    financeFragment.adapterNotify();
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