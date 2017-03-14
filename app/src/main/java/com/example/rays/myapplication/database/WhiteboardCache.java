package com.example.rays.myapplication.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rays on 2017/2/22.
 */
public class WhiteboardCache {
    private static final String TABLE_NAME = "whiteboard";
    private static final String FIELD_ID = "id";
    private static final String FIELD_WHITEBOARD_ID = "whiteboard_id";
    private static final String FIELD_PAGE_NUMBER = "page_number";
    private static final String FIELD_IMAGE_URL = "image_url";
    private static final String FIELD_CONTENT = "content";
    public static final String SQL_CREATE_TABLE;

    static {
        SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FIELD_WHITEBOARD_ID + " INTEGER NOT NULL, " +
                FIELD_PAGE_NUMBER + " INTEGER NOT NULL, " +
                FIELD_IMAGE_URL + " TEXT, " +
                FIELD_CONTENT + " TEXT NOT NULL)";
    }

    public static void insert(WhiteboardCacheEntity entity) {
        SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_WHITEBOARD_ID, entity.whiteboardId);
        values.put(FIELD_PAGE_NUMBER, entity.pageNum);
        values.put(FIELD_IMAGE_URL, entity.imageUrl);
        values.put(FIELD_CONTENT, entity.content);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public static void insert(List<WhiteboardCacheEntity> list) {
        SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values;
            for (WhiteboardCacheEntity entity : list) {
                values = new ContentValues();
                values.put(FIELD_WHITEBOARD_ID, entity.whiteboardId);
                values.put(FIELD_PAGE_NUMBER, entity.pageNum);
                values.put(FIELD_IMAGE_URL, entity.imageUrl);
                values.put(FIELD_CONTENT, entity.content);
                db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void insert(int whiteboardId, int pageNum, String imageUrl, String content) {
        SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_WHITEBOARD_ID, whiteboardId);
        values.put(FIELD_PAGE_NUMBER, pageNum);
        values.put(FIELD_IMAGE_URL, imageUrl);
        values.put(FIELD_CONTENT, content);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public static List<WhiteboardCacheEntity> query(int whiteboardId, int pageNum, int id) {
        List<WhiteboardCacheEntity> list = new ArrayList<>();
        SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, FIELD_WHITEBOARD_ID + "=? AND " + FIELD_PAGE_NUMBER + "=? AND " + FIELD_ID + ">?",
                new String[]{String.valueOf(whiteboardId), String.valueOf(pageNum), String.valueOf(id)},
                null, null, FIELD_ID + " ASC");
        WhiteboardCacheEntity entity;
        while (cursor.moveToNext()) {
            entity = new WhiteboardCacheEntity();
            entity.id = cursor.getInt(cursor.getColumnIndex(FIELD_ID));
            entity.whiteboardId = cursor.getInt(cursor.getColumnIndex(FIELD_WHITEBOARD_ID));
            entity.pageNum = cursor.getInt(cursor.getColumnIndex(FIELD_PAGE_NUMBER));
            entity.imageUrl = cursor.getString(cursor.getColumnIndex(FIELD_IMAGE_URL));
            entity.content = cursor.getString(cursor.getColumnIndex(FIELD_CONTENT));
            list.add(entity);
        }
        cursor.close();
        db.close();
        return list;
    }

    public static List<WhiteboardCacheEntity> query() {
        List<WhiteboardCacheEntity> list = new ArrayList<>();
        SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, FIELD_ID + " ASC");
        WhiteboardCacheEntity entity;
        while (cursor.moveToNext()) {
            entity = new WhiteboardCacheEntity();
            entity.id = cursor.getInt(cursor.getColumnIndex(FIELD_ID));
            entity.whiteboardId = cursor.getInt(cursor.getColumnIndex(FIELD_WHITEBOARD_ID));
            entity.pageNum = cursor.getInt(cursor.getColumnIndex(FIELD_PAGE_NUMBER));
            entity.imageUrl = cursor.getString(cursor.getColumnIndex(FIELD_IMAGE_URL));
            entity.content = cursor.getString(cursor.getColumnIndex(FIELD_CONTENT));
            list.add(entity);
        }
        cursor.close();
        db.close();
        return list;
    }

    public static int delete(int whiteboardId, int pageNum) {
        int result;
        SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
        result = db.delete(TABLE_NAME, FIELD_WHITEBOARD_ID + "=? AND " + FIELD_PAGE_NUMBER + "=?", new String[]{String.valueOf(whiteboardId), String.valueOf(pageNum)});
        db.close();
        return result;
    }

    public static int delete(int whiteboardId) {
        int result;
        SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
        result = db.delete(TABLE_NAME, FIELD_WHITEBOARD_ID + "=?", new String[]{String.valueOf(whiteboardId)});
        db.close();
        return result;
    }

    public static int clear() {
        int result;
        SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
        result = db.delete(TABLE_NAME, null, null);
        db.close();
        return result;
    }
}
