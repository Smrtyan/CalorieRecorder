package com.example.ubento.calorierecorder;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddCaloriaActivity extends AppCompatActivity {
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_caloria);
    }

    public void btn_updateNewCalorie(View view) {
        db = MainActivity.getDB();
        DatePicker dp = findViewById(R.id.dp_addnewday);
        EditText et = findViewById(R.id.et_calorie);
        String dayString;
        dayString=""+dp.getYear();
        dayString=dayString+"-"+String.format("%02d",dp.getMonth()+1);
        dayString= dayString+"-"+String.format("%02d",dp.getDayOfMonth());
        try {
            int calorie = Integer.parseInt(et.getText().toString());
//            Log.d("anum", "" + calorie);
            Log.d("add Calorie", dayString);
            ContentValues values = new ContentValues();
            values.put("day", dayString);
            values.put("calorie", calorie);
            db.insert("dayOfCalorie", null, values);
            et.setText("");
            Toast.makeText(getApplicationContext(), "Data's added successfully: " + dayString + ":" + calorie, Toast.LENGTH_LONG).show();
            db.close();
        }catch (Exception e){
            Toast.makeText(this,et.getText().toString()+" is not a num! Please retry! ",Toast.LENGTH_LONG).show();
            et.setText("");
            Log.e("parseError","Not A Number");
        }
    }
}
