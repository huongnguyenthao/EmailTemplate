package com.example.android.emailtemplate.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.emailtemplate.database.TemplateDbSchema.CategoryTable;
import com.example.android.emailtemplate.database.TemplateDbSchema.TemplateTable;

/**
 * Created by Huong on 9/14/2015.
 */
public class TemplateBaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "templateBase.db";

    public TemplateBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CategoryTable.NAME + "(" +
            " _id integer primary key autoincrement, " +
                CategoryTable.Cols.UUID + ", " +
                CategoryTable.Cols.CATEGORY +
                ") ");
        db.execSQL("create table " + TemplateTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TemplateTable.Cols.CATEGORYUUID + ", " +
                TemplateTable.Cols.UUID + ", " +
                TemplateTable.Cols.NAME + ", " +
                TemplateTable.Cols.ISFAVORITE + ", " +
                TemplateTable.Cols.LASTACCESSED + ", " +
                TemplateTable.Cols.TEMPLATE +
                ") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
