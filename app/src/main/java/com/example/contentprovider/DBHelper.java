package com.example.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHelper extends SQLiteOpenHelper {
    static final String DBNAME = "Android2020";
    static final String TABLE_1 = "Contacts";
    static final String COL_1 = "Name";
    static final String COL_2 = "Number";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table '" + TABLE_1 + "'('"+COL_1+"' varchar(100),'"+COL_2+"' varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table '" + TABLE_1 + "'");
        onCreate(db);
    }

    public boolean InsertEMP(String Name, String Number) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1, Name);
        cv.put(COL_2, Number);

        long l = db.insert(TABLE_1,null,cv);
        if (l > 0) {
            return true;
        } else {
            return false;


        }

    }

    public Cursor getData() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cs = db.rawQuery("select * from '" + TABLE_1 + "'", null);
        return cs;
    }


}
