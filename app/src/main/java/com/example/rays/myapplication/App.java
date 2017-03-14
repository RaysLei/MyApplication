package com.example.rays.myapplication;

import android.app.Application;

import com.example.rays.myapplication.database.DatabaseHelper;

/**
 * Created by Rays on 2017/2/22.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelper.init(this);
    }
}
