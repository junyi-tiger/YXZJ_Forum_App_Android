package com.example.easylearnhome.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseUtil {

    //清空数据库
    public static void clear(SQLiteDatabase database){
        database.delete("user",null,null);
    }

    //获取用户id
    public static Long getUid(SQLiteDatabase database){
        Cursor cursor = database.query("user",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            return cursor.getLong(0);
        }
        return null;
    }

    //获取用户名
    public static String getUname(SQLiteDatabase database){
        Cursor cursor = database.query("user",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            return cursor.getString(1);
        }
        return null;
    }

    //获取邮箱
    public static String getUemail(SQLiteDatabase database){
        Cursor cursor = database.query("user",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            return cursor.getString(3);
        }
        return null;
    }

    //获取密码
    public static String getUpassword(SQLiteDatabase database){
        Cursor cursor = database.query("user",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            return cursor.getString(2);
        }
        return null;
    }

    //获取性别
    public static int getUsex(SQLiteDatabase database){
        Cursor cursor = database.query("user",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            return cursor.getInt(4);
        }
        return 2;
    }

    //获取头像
    public static String getUhead(SQLiteDatabase database){
        Cursor cursor = database.query("user",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            return cursor.getString(5);
        }
        return null;
    }
}
