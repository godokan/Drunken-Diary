package com.godokan.drunkendiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DrunkenDbHelper extends SQLiteOpenHelper {
    private static DrunkenDbHelper drunkenDbHelper = null;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Drunk.db";
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS "+ TableInfo.TABLE_NAME + " (" +
                    TableInfo.COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TableInfo.COLUMN_NAME_DTYPE+" TEXT NOT NULL, " +
                    TableInfo.COLUMN_NAME_DNAME+" TEXT, " +
                    TableInfo.COLUMN_NAME_MEMO+" TEXT, " +
                    TableInfo.COLUMN_NAME_DATE+" TEXT, " +
                    TableInfo.COLUMN_NAME_TIME+" TEXT)";
    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TableInfo.TABLE_NAME;

    public DrunkenDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DrunkenDbHelper getInstance(Context context) {
        if(drunkenDbHelper == null) drunkenDbHelper = new DrunkenDbHelper(context);
        return drunkenDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_TABLE); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
}
