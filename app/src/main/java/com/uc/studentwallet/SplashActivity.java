package com.uc.studentwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import model.User;

public class SplashActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        SharedPreferences preferences = getSharedPreferences("Login", 0);
//        preferences.edit().remove("username").commit();
//        preferences.edit().remove("password").commit();

        SharedPreferences sp1 = this.getSharedPreferences("Login", MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(remember(sp1)){
                    String username = sp1.getString("username", null);
                    String password = sp1.getString("password", null);

                    User user = new User(username, password);
                    login(user);
                    finish();
                } else {
                    intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }

    private boolean remember(SharedPreferences sp1){
        if(sp1.contains("username")){
            return true;
        } else {
            return false;
        }
    }

    private void login(User user){
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
                        startActivity(intent);
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