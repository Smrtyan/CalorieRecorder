package com.example.ubento.calorierecorder;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private static SimpleDBHelper dbHelper;
    public  final int DB_VERSION =1;
    SQLiteDatabase db;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");




    /**
     * Show DatePicker Dialog
     */
    private void showDatePickerDialog(final TextView v) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                v.setText(year+"-"+String.format("%02d",monthOfYear+1)+"-"+String.format("%02d",dayOfMonth));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }


    @Override
    protected void onResume() {

        super.onResume();
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.removeAllSeries();

        Date d;
        db  = MainActivity.getDB();
        Cursor cursor = db.query("dayOfCalorie",
                null,
                null,
                null,
                null, null, "day");
        if (cursor.moveToFirst()) {
            ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();
            try {
                do {

                    String calorie = cursor.getString(cursor.getColumnIndex("calorie"));
                    String day= cursor.getString(cursor.getColumnIndex("day"));
                    d = simpleDateFormat.parse(day);
                    dataPointArrayList.add(new DataPoint(d,Integer.parseInt(calorie)));
//                    Log.v("db-1",day);

                } while (cursor.moveToNext());
                cursor.close();
                LineGraphSeries<DataPoint> series ;
                DataPoint[] dp = new DataPoint[]{};
                dp = dataPointArrayList.toArray(dp);
                series = new LineGraphSeries<DataPoint>(dp);


                graph.addSeries(series);

// set date label formatter
                graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
                graph.getGridLabelRenderer().setNumHorizontalLabels(dataPointArrayList.size()); // only 4 because of the space

// set manual x bounds to have nice steps
//                graph.getViewport().setMinX(d.getTime());
//                graph.getViewport().setMaxX(d.getTime());
                graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
                graph.getGridLabelRenderer().setHumanRounding(false);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        db.close();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new SimpleDBHelper(this, DB_VERSION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        TextView tv;
        tv = (TextView)findViewById(R.id.tv_begin);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog((TextView) v);
            }
        });
        tv = (TextView)findViewById(R.id.tv_end);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog((TextView) v);
            }
        });




    }

    public void btn_edit(View view) {

        Intent intent = new Intent(this,EditCaloriaActivity.class);
        startActivity(intent);
    }
    public static SQLiteDatabase getDB() {
        return dbHelper.getWritableDatabase();
    }

    public void query_click(View view) {
        String statDay = ((TextView)findViewById(R.id.tv_begin)).getText().toString();
        String endDay = ((TextView)findViewById(R.id.tv_end)).getText().toString();
        Intent intent = new Intent(this,QueryActivity.class);
        intent.putExtra("start_day",statDay);
        intent.putExtra("end_day",endDay);
        startActivity(intent);
    }
}
