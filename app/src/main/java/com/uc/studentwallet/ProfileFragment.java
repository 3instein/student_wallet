package com.uc.studentwallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    private Intent intent;
    private View view;
    private TextView profile_full_name, profile_nim;
    private Button profile_log_out;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initComponent();

        profile_full_name.setText(MainActivity.full_name);
        profile_nim.setText(String.valueOf(MainActivity.nim));

        profile_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = v.getContext().getSharedPreferences("Login", 0);
                preferences.edit().remove("username").commit();
                preferences.edit().remove("password").commit();
                intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initComponent() {
        profile_full_name = view.findViewById(R.id.profile_full_name);
        profile_nim = view.findViewById(R.id.profile_nim);
        profile_log_out = view.findViewById(R.id.profile_log_out);
    }
}