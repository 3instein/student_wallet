package com.uc.studentwallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import Adapter.FinanceRVAdapter;
import model.Finance;

public class FinanceFragment extends Fragment {

    private View view;
    private RecyclerView finance_recycleView;
    private FinanceRVAdapter adapter;
    private ArrayList<Finance> financeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_finance, container, false);
        initComponent();
        displayFinance();

        String url = "https://student.hackerexperience.net/finance.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

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
                        Finance finance = new Finance();
                        finance.setFinance_id(userFinance.getInt("id"));
                        finance.setUser_id(userFinance.getInt("user_id"));
                        finance.setAmount(userFinance.getInt("amount"));
                        finance.setStatus(userFinance.getString("status"));
                        financeList.add(finance);
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

        return view;
    }

    private void initComponent() {
        financeList = new ArrayList<>();
        adapter = new FinanceRVAdapter(financeList);
        finance_recycleView = view.findViewById(R.id.finance_recycleView);
    }

    private void displayFinance() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        finance_recycleView.setLayoutManager(layoutManager);
        finance_recycleView.setAdapter(adapter);
    }
}