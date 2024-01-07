package com.example.learnnplay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="LearnNPlayDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_PARENT ="parent_user";
    public static final String KEY_PARENT_ID ="id";
    public static final String KEY_EMAIL ="email";
    public static final String KEY_PASSWORD ="password";
    public static final String TABLE_CHILD ="child_user";
    public static final String KEY_CHILD_NAME ="name";
    public static final String KEY_CHILD_ID ="id";
    public static final String KEY_CHILD_AGE ="age";
    public static final String KEY_SCORE ="score";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PARENT +"("+ KEY_PARENT_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_EMAIL + " TEXT,"+ KEY_PASSWORD + " TEXT" +")");

        db.execSQL("CREATE TABLE " + TABLE_CHILD +"("+ KEY_CHILD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CHILD_NAME + " TEXT,"+ KEY_CHILD_AGE + " INTEGER," + KEY_SCORE + " INTEGER  NOT NULL DEFAULT 0" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHILD);
    }

    public void addParent(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);
        db.insert(TABLE_PARENT, null, values);
    }

    public void addChild(String name, int age){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CHILD_NAME, name);
        values.put(KEY_CHILD_AGE, age);
        db.insert(TABLE_CHILD, null, values);

        Log.e("Child Add", "Child Added");
    }

    public Boolean updateScore(String name, int score){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_SCORE, score);
            long result = -1;

            cursor = db.rawQuery("Select * from " + TABLE_CHILD + " where name = ?", new String[]{name});
            if(cursor.getCount() > 0){
                result = db.update(TABLE_CHILD, values, "name = ?", new String[]{name});
                Log.e("result", "result updated");
            }
            return result != -1;
        } catch (Exception e) {
            // Log the exception
            Log.e("MyDBHelper", "Error updating score", e);
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public int getScore(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        int score = 0;
        Cursor cursor;
        try {
            cursor = db.rawQuery("SELECT " + KEY_SCORE + " FROM " + TABLE_CHILD + " WHERE " + KEY_CHILD_NAME + " = ?", new String[]{name});
            if (cursor != null && cursor.moveToFirst()) {
                int scoreIndex = cursor.getColumnIndex(KEY_SCORE);
                if (scoreIndex != -1) {
                    score = cursor.getInt(scoreIndex);
                } else {
                    // Log error or handle the case where the column is not found
                    Log.e("MyDBHelper", "Column " + KEY_SCORE + " not found.");
                }
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error fetching score", e);
        }
//        finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
        return score;
    }

    public List<String> getChildData(){
        List<String> childrenNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_CHILD, null);
        if (cursor.moveToFirst()) {
            do {
                childrenNames.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return childrenNames;
    }
}
