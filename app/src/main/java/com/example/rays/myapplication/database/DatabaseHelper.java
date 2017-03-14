package com.example.rays.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rays on 2017/2/22.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String name = "my";
    private static final int version = 1;
    private static DatabaseHelper dbHelper;

    public static void init(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public static DatabaseHelper getInstance() {
        return dbHelper;
    }

    private DatabaseHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WhiteboardCache.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
