package com.uc.studentwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    static int id = 0;
    static int balance = 0;
    static String nim = "", username = "";

    private Intent intent;
    private BottomNavigationView main_bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initValue();
        initComponent();

        main_bottom_nav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull @NotNull MenuItem item) {
                Fragment fragment = null;

                if (item.getItemId() == R.id.nav_home) {
                    fragment = new HomeFragment();
                    Bundle data = new Bundle();
                    data.putInt("id", id);
                    data.putString("username", username);

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
        nim = String.valueOf(intent.getIntExtra("nim", 0));
        username = String.valueOf(intent.getIntExtra("username", 0));
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new HomeFragment()).commit();
    }

    private void initComponent() {
        main_bottom_nav = findViewById(R.id.main_bottom_nav);
    }
}