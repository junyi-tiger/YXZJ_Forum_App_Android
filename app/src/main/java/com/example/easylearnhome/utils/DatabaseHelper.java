package com.example.easylearnhome.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String db_name = "user.db";//数据库名称
    private static final int version = 1;//数据库版本

    public DatabaseHelper(Context context){
        super(context,db_name,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("database","没有数据库，创建");
        //建表语句
        String sql_user = "create table user (" +
                "uid integer," +
                "uname varchar(50)," +
                "upassword varchar(50)," +
                "uemail varchar(50)," +
                "usex integer," +
                "uhead varchar(50)," +
                "ustate integer," +
                "urole integer," +
                "register_time timestamp )";
        sqLiteDatabase.execSQL(sql_user);
        Log.d("database","创建了user表");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("database","数据库更新了！");
    }
}
