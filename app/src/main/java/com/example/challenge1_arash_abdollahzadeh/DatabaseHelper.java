package com.example.challenge1_arash_abdollahzadeh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
//    private static final String TABLE_NAME = "image_table.db";
    //private static final String COL1 = "ID";
    //private static final String COL2 = "BLOB";


    public DatabaseHelper(Context context){
        super(context, "imageDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table images(id INTEGER PRIMARY KEY AUTOINCREMENT, img blob not null)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists images");
        onCreate(db);
    }

    public boolean addData(byte[] data, int i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put("imaID", i);
        contentValues.put("img", data);
        db.insert("images", null, contentValues);
        long result = db.insert("images", null, contentValues);

        if (result == -1) return false;
        else return true;
    }

    // get image
    public Bitmap getimage(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("select * from images where id=?", new String[]{String.valueOf(id)});
        if (cursor.moveToNext()){
            byte[] imag = cursor.getBlob(1);
            bt = BitmapFactory.decodeByteArray(imag, 0, imag.length);
        }
        return bt;
    }
}
