package com.example.meals;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DB_sqlite extends SQLiteOpenHelper {
    public DB_sqlite(Context context) {
        super(context, "happy_meal", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table user " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "email TEXT," +"pass TEXT,"+"ismanger TEXT,"+"date TEXT"+
                ")");

        sqLiteDatabase.execSQL("create table meal " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "name TEXT," +"description TEXT,"+"day TEXT,"+"image TEXT"+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS meal");
        onCreate(sqLiteDatabase);
    }

    // insert new user  account
    public boolean insert_user (String email,String pass, String ismanger){
        SQLiteDatabase dp = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email",email);
        values.put("pass",pass);
        values.put("ismanger",ismanger);
        long result = dp.insert("user",null,values);
        if(result == -1)
            return false;
        else
            return  true;
    }

    // insert new meal to list
    public boolean insert_meal (String name,String description, String day, String image){
        SQLiteDatabase dp = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("description",description);
        values.put("day",day);
        values.put("image",image);
        long result = dp.insert("meal",null,values);
        if(result == -1)
            return false;
        else
            return  true;
    }

    // check if any user have this email & pass
    public String have_user(String email,String pass){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        try {
            Cursor res = db.rawQuery("select * from user WHERE pass = " + pass, null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                String emaiil = res.getString(1);
                String passs = res.getString(2);
                String id = res.getString(0);
                String manger = res.getString(3);
                if (emaiil.equals(email) && passs.equals(pass))
                    return id+"_/_"+manger;

                res.moveToNext();
            }
        }catch (Exception e){
            System.out.println(e.getMessage().toString());
        }
        return "";
    }

    // get meal for current day
    public ArrayList get_meals (String date){

            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> array_list = new ArrayList<String>();
            try {
                Cursor res = db.rawQuery("select * from meal", null);
                res.moveToFirst();
                while (res.isAfterLast() == false) {
                    String name = res.getString(1);
                    String des = res.getString(2);
                    String image = res.getString(4);
                    String day = res.getString(3);
                    if(day.equals(date))
                        array_list.add(name + "_/_" + des + "_/_" + image+"_/_"+day);
                    res.moveToNext();
                }
            }catch (Exception e){
                System.out.println(e.getMessage().toString());
            }
            return array_list;
    }

    // confirm that user choose it's meal
    public boolean  confirm (String id,String date){
        SQLiteDatabase dp = this.getWritableDatabase();
        ContentValues data=new ContentValues();
        data.put("date",date);
        long result = dp.update("user",data,"id = ?",new String[]{id});
        System.out.println(result);
        if(result==-1)
            return false;
        else
        return  true;
    }

    // get meal before
    public boolean have_meal (String uid, String date){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        try {
            Cursor res = db.rawQuery("select * from user", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                String id = res.getString(0);
                String day = res.getString(4);
                if (uid.equals(id) && day.equals(date))
                    return true;

                res.moveToNext();
            }
        }catch (Exception e){
            System.out.println(e.getMessage().toString());
        }
        return false;
    }
}
