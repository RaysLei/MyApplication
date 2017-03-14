package com.example.rays.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rays.myapplication.R;
import com.example.rays.myapplication.database.WhiteboardCache;
import com.example.rays.myapplication.database.WhiteboardCacheEntity;

import java.util.List;

public class DatabaseActivity extends AppCompatActivity {
    private TextView text1;
    private int whiteboardId = 1;
    private int pageNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        text1 = (TextView) findViewById(android.R.id.text1);
    }

    public void onInit(View view) {

    }

    public void onInsert(View view) {
        Log.i(getClass().getSimpleName(), "insert: whiteboardId=" + whiteboardId + " pageNum=" + pageNum);
        WhiteboardCache.insert(whiteboardId, pageNum, "imageUrl test", "content test");
        pageNum++;
        if (pageNum % 5 == 0) {
            whiteboardId++;
        }
        showResult();
    }

    public void onQuery(View view) {
        showResult();
    }

    public void onDelete(View view) {
        int delWhiteboardId = (int) (Math.random() * (whiteboardId - 1) + 1);
        int delPageNum = (int) (Math.random() * (pageNum - 1) + 1);
        int row = WhiteboardCache.delete(delWhiteboardId, delPageNum);
        Log.i(getClass().getSimpleName(), "delete: delWhiteboardId=" + delWhiteboardId + " delPageNum=" + delPageNum + " row=" + row);
        showResult();
    }

    public void onClear(View view) {
        WhiteboardCache.clear();
        showResult();
    }

    private void showResult() {
        List<WhiteboardCacheEntity> list = WhiteboardCache.query();
        if (list.isEmpty()) {
            text1.setText("null");
        } else {
            text1.setText(null);
            for (WhiteboardCacheEntity entity : list) {
                text1.append(entity.toString());
                text1.append("\n");
            }
        }
        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
    }

}
