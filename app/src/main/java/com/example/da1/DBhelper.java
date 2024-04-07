package com.example.da1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper   extends SQLiteOpenHelper {
    public DBhelper(@Nullable Context context) {
        super(context, "DBfood", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String DBfood ="CREATE TABLE food (\n" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  name text,\n" +
                "  price INTEGER  ,\n" +
                "  soluong INTEGER\n" +
                ")";
        db.execSQL(DBfood);

        String DBban = "create table ban (ban interger )";
        db.execSQL(DBban);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i != i1){
            db.execSQL("Drop table if exists food");
            onCreate(db);
        }
    }
}
