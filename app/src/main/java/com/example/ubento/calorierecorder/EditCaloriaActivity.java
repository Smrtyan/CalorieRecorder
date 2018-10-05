package com.example.ubento.calorierecorder;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditCaloriaActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_caloria);
    }
     void deleteDataFromTextViewAndDB(final TextView tv){
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setTitle("Warnings!");
         builder.setMessage("Are you sure to delete this record?");
         builder.setPositiveButton("yes",
             (dialog, which) ->{
                LinearLayout layout = (LinearLayout) tv.getParent();
                layout.removeView(tv);
                 db = MainActivity.getDB();
                 ContentValues values = new ContentValues();
                 values.put("isHidden",1);
                 String [] array = tv.getText().toString().split(" : ");
                 Toast.makeText(getApplicationContext(),array[0],Toast.LENGTH_LONG).show();
                 db.update("dayOfCalorie",values,"day = ? and calorie = ?",array);
                 db.close();
             }
         );
         builder.setNegativeButton("no", (dialog,which)->{ });
         AlertDialog dialog = builder.create();
         dialog.show();

     }
    @Override
    protected void onResume() {
        super.onResume();
        db = MainActivity.getDB();
        String WHERE_CLAUSE = "isHidden = 0";
        Cursor cursor = db.query("dayOfCalorie",
                null,
                WHERE_CLAUSE,
                null,
                null, null, "day");
        TextView textView;
        LinearLayout linear = findViewById(R.id.ll);
        linear.removeAllViews();
        if (cursor.moveToFirst()) {
            do {
                ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();


                String calorie = cursor.getString(cursor.getColumnIndex("calorie"));
                String day = cursor.getString(cursor.getColumnIndex("day"));
                textView = new TextView(this);
                textView.setText(day + " : " + calorie);
                textView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deleteDataFromTextViewAndDB((TextView)view);
                        return true;
                    }
                });
           //     textView.setOnClickListener();
                linear.addView(textView);


            }while(cursor.moveToNext());
            cursor.close();
            db.close();
        }

    }

    public void btn_add(View view) {
        Intent intent = new Intent(this,AddCaloriaActivity.class);
        //add string here

        startActivity(intent);
    }
}
