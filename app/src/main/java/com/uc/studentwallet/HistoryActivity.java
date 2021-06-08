package com.uc.studentwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.HistoryRVAdapter;
import model.History;

public class HistoryActivity extends AppCompatActivity {

    private ImageView history_back_btn;
    private RecyclerView history_recyclerView;
    private HistoryRVAdapter adapter;
    private ArrayList<History> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initComponent();
        displayHistory();

        String url = "https://student.hackerexperience.net/transaction.php";
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
                    JSONArray jsonArrayHistory = response.getJSONArray("userTransaction");

                    for (int i = 0; i < jsonArrayHistory.length(); i++) {
                        JSONObject userHistory = jsonArrayHistory.getJSONObject(i);
                        if (userHistory.getString("transaction_type").equalsIgnoreCase("Transfer")) {
                            String transaction_type = userHistory.getString("transaction_type");
                            String timestamp = userHistory.getString("timestamp");
                            String destination = userHistory.getString("destination");
                            String sender = userHistory.getString("sender");
                            String destination_nim = userHistory.getString("destination_nim");
                            String sender_nim = userHistory.getString("sender_nim");
                            int amount = userHistory.getInt("amount");
                            History newHistory = new History(transaction_type, timestamp, destination, sender, destination_nim, sender_nim, amount);
                            historyList.add(newHistory);
                        } else {
                            String transaction_type = userHistory.getString("transaction_type");
                            String timestamp = userHistory.getString("timestamp");
                            int amount = userHistory.getInt("amount");
                            History newHistory = new History(transaction_type, timestamp, amount);
                            historyList.add(newHistory);
                        }
                    }
                    adapter.notifyDataSetChanged();
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
        historyList = new ArrayList<>();
        adapter = new HistoryRVAdapter(historyList);
        history_back_btn = findViewById(R.id.history_back_btn);
        history_recyclerView = findViewById(R.id.history_recyclerView);
    }

    private void displayHistory() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        history_recyclerView.setLayoutManager(layoutManager);
        history_recyclerView.setAdapter(adapter);
    }
}