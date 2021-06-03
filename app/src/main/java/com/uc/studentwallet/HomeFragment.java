package com.uc.studentwallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    private View view;
    private TextView home_username, home_card_nim, home_card_full_name, home_card_balance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initComponent();

        Bundle data = getArguments();

        if (data != null) {
            home_username.setText(data.getString("username"));
            home_card_nim.setText(String.valueOf(data.getInt("nim")));
            home_card_balance.setText(String.valueOf(data.getInt("balance")));
            home_card_full_name.setText(data.getString("full_name"));
        }

        return view;
    }

    private void initComponent() {
        home_username = view.findViewById(R.id.home_username);
        home_card_nim = view.findViewById(R.id.home_card_nim);
        home_card_full_name = view.findViewById(R.id.home_card_full_name);
        home_card_balance = view.findViewById(R.id.home_card_balance);
    }
}