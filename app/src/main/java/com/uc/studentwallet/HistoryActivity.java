package com.uc.studentwallet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.User;

public class HistoryActivity extends AppCompatActivity {

    private User user;
    private Intent intent;
    private ImageView history_back_btn;
    private RecyclerView history_recyclerView;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initComponent();

        String url = "https://student.hackerexperience.net/history.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", user.getUser_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject userHistory = response.getJSONObject("userHistory");
                    String message = userHistory.getString("message");

                    if (message.equalsIgnoreCase("success")) {
                        String type = jsonObject.getString("type");
                        test.setText(type);
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

        history_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initComponent() {
        history_back_btn = findViewById(R.id.history_back_btn);
        history_recyclerView = findViewById(R.id.history_recyclerView);
        test = findViewById(R.id.test);
    }
}