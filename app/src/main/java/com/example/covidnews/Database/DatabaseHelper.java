package com.example.covidnews.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static String TAG = "DataBaseHelper";

    private static int version = 1;
    private SQLiteDatabase _dataBase;
    private final Context _context;
    public  static String DB_NAME = "Login.db";

    public static final String TABLE_NAME = "USER";
    public static final String COLUMN_FULL_NAME = "FULL_NAME";
    public static final String COLUMN_PHONE_NUM = "PHONE_NUMBER";
    public static final String COLUMN_PASSWORD = "PASSWORD";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PHONE_NUM + " INTEGER PRIMARY KEY," +
                    COLUMN_FULL_NAME + " TEXT," +
                    COLUMN_PASSWORD + " TEXT)";

    public DatabaseHelper(@Nullable Context context){
        super(context, DB_NAME, null, version);
        _context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists " + TABLE_NAME);
    }

    public boolean insert(String full_name, String phone_num, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COLUMN_FULL_NAME, full_name);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_PHONE_NUM, phone_num);
        long ins = db.insert(TABLE_NAME, null, contentValues);
        return ins != -1;
    }

    public  boolean isPhoneNumberExists(String phone_num){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME +" where " + COLUMN_PHONE_NUM + " = ?", new String [] {phone_num});
        return cursor.getCount() <= 0;
    }

    public boolean checkAccount(String phone_num, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME +
                " where "+COLUMN_PHONE_NUM+" = ? and "+
                COLUMN_PASSWORD+" = ?",
                new String [] {phone_num, password});

        return cursor.getCount() > 0;
    }

    public  void updatePassword(String phone, String new_pw){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE "+TABLE_NAME+" SET "+COLUMN_PASSWORD+" = "+new_pw+" WHERE "+COLUMN_PHONE_NUM+" = "+ phone;
        db.execSQL(strSQL);
    }

    public String getName(String phone_num){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select "+ COLUMN_FULL_NAME + " from "+ TABLE_NAME +" where " + COLUMN_PHONE_NUM + "= ?", new String[]{phone_num});
        cursor.moveToFirst();
        return cursor.getString(0);
    }

}
