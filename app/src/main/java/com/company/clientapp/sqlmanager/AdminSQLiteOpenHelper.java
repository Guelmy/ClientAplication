package com.company.clientapp.sqlmanager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Company(id INTEGER PRIMARY KEY AUTOINCREMENT, name text)");
        db.execSQL("create table Client(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name text, " +
                "company_id INTEGER, " +
                "FOREIGN KEY(company_id) REFERENCES Company(id))");
        db.execSQL("create table Address(" +
                "name text, " +
                "client_id INTEGER," +
                "FOREIGN KEY(client_id) REFERENCES Client(id) )");

//        db.execSQL("INSERT INTO Company (name) VALUES ('OrionTek')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Company");
        db.execSQL("DROP TABLE IF EXISTS Client");
        db.execSQL("DROP TABLE IF EXISTS Address");

        onCreate(db);
    }
}
