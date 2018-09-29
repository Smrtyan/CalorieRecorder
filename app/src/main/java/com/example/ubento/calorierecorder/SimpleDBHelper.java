package com.example.ubento.calorierecorder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SimpleDBHelper extends SQLiteOpenHelper {

    private static final String DBName = "Calorie.db";

    private static final String DAY_OF_CALORIE = "dayOfCalorie";

    private static final String CREATE_DAY_OF_CALORIE_TABLE
            = "create table " + DAY_OF_CALORIE + "(id integer primary key autoincrement,day text, calorie text)";

//    private static final String UPDATE_STUDENT_TABLE
//            = "alter table " + DAY_OF_CALORIE + " add height integer";


    public SimpleDBHelper(Context context, int version) {
        super(context, DBName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DAY_OF_CALORIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        switch (i) {
            case 1:
                //upgrade logic from 1 to 2
             //   sqLiteDatabase.execSQL(CREATE_DAY_OF_CALORIE_TABLE);
            case 2:
                // upgrade logic from 2 to 3

                break;
            default:
                throw new IllegalStateException("unknown oldVersion " + i);
        }

    }
}
