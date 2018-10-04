package com.example.ubento.calorierecorder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QueryActivity extends AppCompatActivity {

    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        Intent intent = getIntent();
        String statDay = intent.getStringExtra("start_day");
        String endDay = intent.getStringExtra("end_day");
        db  = MainActivity.getDB();
        String whereclause = "day > ?" + " and day < ?"+"and isHidden = 0";
        Cursor cursor = db.query("dayOfCalorie",
                null,
                whereclause,
                new String[]{
                        statDay,endDay
                },
                null, null, "day");
        TextView textView ;
        LinearLayout linearLayout = findViewById(R.id.linear_query);
        if (cursor.moveToFirst()) {

            do {
                textView = new TextView(this);
                String calorie = cursor.getString(cursor.getColumnIndex("calorie"));
                String day = cursor.getString(cursor.getColumnIndex("day"));
                textView.setText(day+" : "+calorie);
                linearLayout.addView(textView);

            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
    }
}
