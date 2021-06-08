package com.uc.studentwallet;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private Intent intent;
    private View view;
    private TextView home_username, home_card_nim, home_card_full_name, home_card_balance;
    private ImageView home_topUp_btn, home_transfer_btn, home_history_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initComponent();

        Bundle data = getArguments();

        if (data != null) {
            home_username.setText(data.getString("username"));
            home_card_nim.setText(String.valueOf(MainActivity.nim));
            home_card_balance.setText(formatter((double) MainActivity.balance));
            home_card_full_name.setText(data.getString("full_name"));
        }

        home_topUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(view.getContext(), TopUpActivity.class);
                intent.putExtra("balance", MainActivity.balance);
                startActivity(intent);
            }
        });

        home_transfer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(view.getContext(), TransferActivity.class);
                startActivity(intent);
            }
        });

        home_history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(view.getContext(), HistoryActivity.class);
                intent.putExtra("id", data.getInt("id"));
                startActivity(intent);
            }
        });

        return view;
    }

    private void initComponent() {
        home_username = view.findViewById(R.id.home_username);
        home_card_nim = view.findViewById(R.id.home_card_nim);
        home_card_full_name = view.findViewById(R.id.home_card_full_name);
        home_card_balance = view.findViewById(R.id.home_card_balance);
        home_topUp_btn = view.findViewById(R.id.home_topUp_btn);
        home_transfer_btn = view.findViewById(R.id.home_transfer_btn);
        home_history_btn = view.findViewById(R.id.home_history_btn);
    }

    public static String formatter(double balance) {
        Locale localeId = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(localeId);

        String formattedBalance = format.format(balance);

        return formattedBalance;
    }

    public static String dateSplitter(String date) throws ParseException {
        String[] newDate = date.split("\\s");
        String oldDate = newDate[0];
        return dateFormatter(oldDate);
    }

    public static String dateFormatter(String oldDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(oldDate);
        dateFormat.applyPattern("dd MMMM yyyy");
        String newDate = dateFormat.format(date);

        SimpleDateFormat newFormat = new SimpleDateFormat("dd MMMM yyyy");
        Date date1 = newFormat.parse(newDate);

        SimpleDateFormat lastFormat = new SimpleDateFormat("dd MMMM yyyy");
        String lastDate = lastFormat.format(date1);

        return lastDate;
    }
}