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
    public  final int DB_VERSION =2;
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


        String startDay,endDay;
        startDay = "2018-10-01";
        endDay = "2018-10-06";

        GraphView graph = (GraphView) findViewById(R.id.graph);

        Date date;
        db  = MainActivity.getDB();
        LineGraphSeries<DataPoint> series ;
        DataPoint[] dataPoints = new DataPoint[]{};
        String WHERE_CLAUSE = "day >= ? AND day <= ? AND isHidden = 0";
        Cursor cursor = db.query("dayOfCalorie",
                null,
                WHERE_CLAUSE,
                new String[]{
                        startDay,endDay
                },
                null, null, "day");
        if (cursor.moveToFirst()) {
            ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();
            try {
                do {

                    String calorie = cursor.getString(cursor.getColumnIndex("calorie"));
                    String day= cursor.getString(cursor.getColumnIndex("day"));
                    date = simpleDateFormat.parse(day);
                    dataPointArrayList.add(new DataPoint(date,Integer.parseInt(calorie)));
                    Log.v("db-1",day);
                } while (cursor.moveToNext());
                dataPoints = dataPointArrayList.toArray(dataPoints);
                series = new LineGraphSeries<>(dataPoints);
                graph.addSeries(series);

                graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(MainActivity.this));

                graph.getGridLabelRenderer().setNumHorizontalLabels(dataPoints.length);

                graph.getViewport().setMinX(simpleDateFormat.parse(startDay).getTime());
                graph.getViewport().setMaxX(simpleDateFormat.parse(endDay).getTime());
                graph.getViewport().setXAxisBoundsManual(true);

                graph.getGridLabelRenderer().setHumanRounding(false);

            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("graphView", "error");
            }

        }
        cursor.close();
        db.close();






    }

    public void btn_edit(View view) {

        Intent intent = new Intent(this,EditCaloriaActivity.class);
        startActivity(intent);
    }
    public static SQLiteDatabase getDB() {
        return dbHelper.getWritableDatabase();
    }

    public void query_click(View view) {
        String startDay = ((TextView)findViewById(R.id.tv_begin)).getText().toString();
        String endDay = ((TextView)findViewById(R.id.tv_end)).getText().toString();
//        Intent intent = new Intent(this,QueryActivity.class);
//        intent.putExtra("start_day",statDay);
//        intent.putExtra("end_day",endDay);
//        startActivity(intent);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.removeAllSeries();

        Date date;
        db  = MainActivity.getDB();
        LineGraphSeries<DataPoint> series ;
        DataPoint[] dataPoints = new DataPoint[]{};
        String WHERE_CLAUSE = "day >= ? AND day <= ? AND isHidden = 0";
        Cursor cursor = db.query("dayOfCalorie",
                null,
                WHERE_CLAUSE,
                new String[]{
                        startDay,endDay
                },
                null, null, "day");
        if (cursor.moveToFirst()) {
            ArrayList<DataPoint> dataPointArrayList = new ArrayList<>();
            try {
                do {

                    String calorie = cursor.getString(cursor.getColumnIndex("calorie"));
                    String day= cursor.getString(cursor.getColumnIndex("day"));
                    date = simpleDateFormat.parse(day);
                    dataPointArrayList.add(new DataPoint(date,Integer.parseInt(calorie)));
                    Log.v("db-1",day);
                } while (cursor.moveToNext());
                dataPoints = dataPointArrayList.toArray(dataPoints);
                series = new LineGraphSeries<>(dataPoints);
                graph.addSeries(series);

                graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(MainActivity.this));

                graph.getGridLabelRenderer().setNumHorizontalLabels(dataPoints.length);

                graph.getViewport().setMinX(simpleDateFormat.parse(startDay).getTime());
                graph.getViewport().setMaxX(simpleDateFormat.parse(endDay).getTime());
                graph.getViewport().setXAxisBoundsManual(true);

                graph.getGridLabelRenderer().setHumanRounding(false);

            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("graphView", "error");
            }

        }
        cursor.close();
        db.close();





    }

}
