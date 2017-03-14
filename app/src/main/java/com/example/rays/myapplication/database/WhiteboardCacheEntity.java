package com.example.rays.myapplication.database;

/**
 * Created by Rays on 2017/2/23.
 */

public class WhiteboardCacheEntity {
    public int id;
    public int whiteboardId;
    public int pageNum;
    public String imageUrl;
    public String content;

    @Override
    public String toString() {
        return "id=" + id + " whiteboardId=" + whiteboardId + " pageNum=" + pageNum + " imageUrl=" + imageUrl + " content=" + content;
    }
}
