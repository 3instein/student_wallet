package com.uc.studentwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    static int id = 0, balance = 0, nim;
    static String username = "", full_name = "";

    private Intent intent;
    private Fragment fragment;
    private BottomNavigationView main_bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initValue();
        initComponent();

        fragment = new HomeFragment();
        Bundle data = new Bundle();
        data.putInt("id", id);
        data.putInt("nim", nim);
        data.putInt("balance", balance);
        data.putString("username", username);
        data.putString("full_name", full_name);

        fragment.setArguments(data);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).commit();

        main_bottom_nav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull @NotNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    fragment = new HomeFragment();
                    Bundle data = new Bundle();
                    data.putInt("id", id);
                    data.putInt("nim", nim);
                    data.putInt("balance", balance);
                    data.putString("username", username);
                    data.putString("full_name", full_name);

                    fragment.setArguments(data);
                } else if (item.getItemId() == R.id.nav_finance) {

                } else if (item.getItemId() == R.id.nav_profile) {

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).commit();
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
        fragment = null;
        main_bottom_nav = findViewById(R.id.main_bottom_nav);
    }
}