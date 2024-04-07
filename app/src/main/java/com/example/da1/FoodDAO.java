package com.example.da1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.da1.models.FoodSP;

import java.sql.Connection;
import java.util.ArrayList;

public class FoodDAO {
    public DBhelper dBhelper;
    public SQLiteDatabase database;
    private Connection connection;
//    public FoodDAO(Connection connection) {
//        this.connection = connection;
//    }

    public FoodDAO(Context context){
        dBhelper = new DBhelper(context);
        database = dBhelper.getWritableDatabase();
    }
    public ArrayList<FoodSP> getDSFOOD(){
        ArrayList<FoodSP> itemList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dBhelper.getReadableDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("select * from food",null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                do{
                    itemList.add(new FoodSP(cursor.getString(0), cursor.getInt(1),cursor.getInt(2)));
                }while (cursor.moveToNext());

        }
        }catch (Exception exception){
            Log.e("exf", "getDSFOOD: " + exception.getMessage());
        }
        return itemList;

    }
    public boolean themItemFood(FoodSP f){
        SQLiteDatabase sqLiteDatabase = dBhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",f.getName());
        contentValues.put("price",f.getGia());
        contentValues.put("soluong",f.getSoluong());

        long check = sqLiteDatabase.insert("food",null,contentValues);
        if (check == -1){
            return false;
        }else {
            return true;
        }

    }
    public boolean suaSoluongFood(String name, int sl ){
        SQLiteDatabase sqLiteDatabase = dBhelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("soluong",sl);


        int check = sqLiteDatabase.update("food",contentValues,"name =?",new String[]{String.valueOf(name)});
        return check!=0;
    }
    public boolean xoaFood(String food){
        SQLiteDatabase sqLiteDatabase = dBhelper.getWritableDatabase();

        int check = sqLiteDatabase.delete("food","name = ?",new String[]{String.valueOf(food)});
        return check != 0;
    }
//    public void deleteAllOrders() throws SQLException, SQLException {
//        String sql = "DELETE FROM food";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.executeUpdate();
//    }
}

