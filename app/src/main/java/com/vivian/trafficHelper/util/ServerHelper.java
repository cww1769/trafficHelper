package com.vivian.trafficHelper.util;

import android.util.Log;

public class ServerHelper {
    private static final String TAG = "ServerHelper";
    public static LoginStatusCode login(String phone, String passwd){
        Log.i(TAG, "begin to login with phone nubmer = " + phone + " with passwd = " + passwd);
        //send rest GET to server and insert the data to the db

        return LoginStatusCode.SUCCESS;
    }

    public static LoginStatusCode signup(String phone, String passwd){
        Log.i(TAG, "begin to signup with phone nubmer = " + phone + " with passwd = " + passwd);
        //send rest PUT to server and insert the data to the db
        return LoginStatusCode.SUCCESS;
    }

}
