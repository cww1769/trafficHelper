package com.vivian.trafficHelper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    //支持同一个业主属于多个小区,用空格隔开
    public static final String CREATE_USERINFO = "create table userInfo(" +
            "phone text primary key," +
            "role Integer not null," +
            "lastLoginTime Integer not null," +
            "username text," +
            "passwd text," +
            "realname text," +
            "community text," +
            "address text," +
            "age integer," +
            "gender integer)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory,int version){
        super(context,name,cursorFactory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERINFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
