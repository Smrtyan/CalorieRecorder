package com.example.ubento.calorierecorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QueryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        Intent intent = getIntent();
        String statDay = intent.getStringExtra("start_day");
        String endDay = intent.getStringExtra("end_day");

    }
}
