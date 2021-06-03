package com.uc.studentwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static int id = 0;
    static int balance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initValue();

    }

    public void initValue(){
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        balance = intent.getIntExtra("balance", 0);
    }

}