package com.example.rays.myapplication.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.rays.myapplication.R;
import com.example.rays.myapplication.fragment.StatusFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "status";
    private LockBroadcastReceiver lockBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lockBroadcastReceiver = new LockBroadcastReceiver();
        lockBroadcastReceiver.register();

        getSupportFragmentManager().beginTransaction().add(R.id.container, new StatusFragment()).commit();

        Log.i(getClass().getSimpleName(), "TimeZone: " + TimeZone.getDefault().getID());
        Log.i(getClass().getSimpleName(), "System currentTimeMillis: " + System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        Log.i(getClass().getSimpleName(), "Calendar TimeInMillis: " + calendar.getTimeInMillis());

        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        calendar = Calendar.getInstance(timeZone, Locale.US);
        Log.i(getClass().getSimpleName(), "Calendar UTC US TimeInMillis: " + calendar.getTimeInMillis() + " " + timeZone.getID());

        // 2017-02-06T02:17:45Z  2017/2/6 10:18
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        try {
            Date date = sdf.parse("2017/02/06 10:18");
            Log.i(getClass().getSimpleName(), "Date Time: " + date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = simpleDateFormat.parse("2017-02-06T02:17:45Z");
            Log.i(getClass().getSimpleName(), "Date Time: " + date.getTime());
            Log.i(getClass().getSimpleName(), "Date: " + sdf.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart isAppOnForeground=" + isAppOnForeground());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume isAppOnForeground=" + isAppOnForeground());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause isAppOnForeground=" + isAppOnForeground());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop isAppOnForeground=" + isAppOnForeground());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy isAppOnForeground=" + isAppOnForeground());
        lockBroadcastReceiver.unregister();
    }

    /**
     * 是否在前台
     * @return
     */
    public int isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        /**
         * 获取Android设备中所有正在运行的App
         */
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return -1;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(getPackageName())) {
//                Log.i(TAG, "importance=" + appProcess.importance);
//                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
                return appProcess.importance;
            }
        }
        return -1;
    }

    public void onStartList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private class LockBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "LockBroadcastReceiver " + intent.getAction() + " isUIThread=" + (Looper.myLooper() == Looper.getMainLooper()));
            switch (intent.getAction()) {
                case Intent.ACTION_SCREEN_OFF:
                    Log.i(TAG, "LockBroadcastReceiver 锁屏");
                    break;
                case Intent.ACTION_SCREEN_ON:
                    Log.i(TAG, "LockBroadcastReceiver 开屏");
                    break;
                case Intent.ACTION_USER_PRESENT:
                    Log.i(TAG, "LockBroadcastReceiver 解锁");
                    break;
            }
        }

        void register() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_SCREEN_ON);
            intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
            intentFilter.addAction(Intent.ACTION_USER_PRESENT);
            registerReceiver(this, intentFilter);
        }

        void unregister() {
            unregisterReceiver(this);
        }
    }
}
