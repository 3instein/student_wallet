package com.uc.studentwallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import Adapter.FinanceRVAdapter;
import model.FInance;

public class FinanceFragment extends Fragment {

    private View view;
    private RecyclerView finance_recycleView;
    private FinanceRVAdapter adapter;
    private ArrayList<FInance> financeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_finance, container, false);
        initComponent();
        displayFinance();

        return view;
    }

    private void initComponent() {
        adapter = new FinanceRVAdapter(financeList);
        financeList = new ArrayList<>();
        finance_recycleView = view.findViewById(R.id.finance_recycleView);
    }

    private void displayFinance() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        finance_recycleView.setLayoutManager(layoutManager);
        finance_recycleView.setAdapter(adapter);
    }

    public void addFinanceList(FInance fInance) {
        financeList.add(fInance);
    }

    public void adapterNotify() {
        adapter.notifyDataSetChanged();
    }
}