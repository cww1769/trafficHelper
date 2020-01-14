package com.vivian.trafficHelper;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vivian.trafficHelper.data.UserInfo;
import com.vivian.trafficHelper.db.MyDatabaseHelper;
import com.vivian.trafficHelper.util.config;

import java.util.LinkedList;
import java.util.List;

public class MyApplication extends Application {

    private static MyApplication instance = null;

    public static boolean isLogged() {
        return isLogged;
    }

    public static void setIsLogged(boolean isLogged) {
        MyApplication.isLogged = isLogged;
    }

    private static boolean isLogged = false;
    public static MyApplication getInstance(){
        return instance;
    }
    public MyApplication() {
        super();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, config.userInfoDB,null,1);
        users = getUserInfoInDB(dbHelper);
    }

    private List<UserInfo> users = null;

    private List<UserInfo> getUserInfoInDB(MyDatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Query = "Select * from userInfo order by ?";
        Cursor cursor = db.rawQuery(Query, new String[]{"lastLoginTime"});
        if (cursor.getCount() == 0)
            return null;
        List<UserInfo> allUserInfo = new LinkedList<UserInfo>();
        if (cursor.moveToFirst()) {
            do {
                UserInfo info = new UserInfo();
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                info.setPhone(phone);
                int role = cursor.getInt(cursor.getColumnIndex("role"));
                info.setRole(role);
                long lastLoginTime = cursor.getInt(cursor.getColumnIndex("lastLoginTime"));
                info.setLastLoginTime(lastLoginTime);
                String username = cursor.getString(cursor.getColumnIndex("username"));
                info.setUsername(username);
                String passwd = cursor.getString(cursor.getColumnIndex("passwd"));
                info.setPasswd(passwd);
                String realname = cursor.getString(cursor.getColumnIndex("realname"));
                info.setRealname(realname);
                String community = cursor.getString(cursor.getColumnIndex("community"));
                info.setCommunity(community);
                String address = cursor.getString(cursor.getColumnIndex("address"));
                info.setAddress(address);
                int age = cursor.getInt(cursor.getColumnIndex("age"));
                info.setAge(age);
                int gender = cursor.getInt(cursor.getColumnIndex("gender"));
                info.setGender(gender);
                allUserInfo.add(info);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return allUserInfo;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

}
