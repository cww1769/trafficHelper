package com.vivian.trafficHelper.util;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vivian.trafficHelper.R;

import java.util.regex.Pattern;

public class Utils {
    public static final String ERRPHONE = "请输入合法手机号";
    public static final String ERRPASS = "请输入4到10位的字符或数字";

    public static boolean isValidPhoneNumber(String number) {
        String pattern = "^1[0-9]{10}$";
        return  Pattern.matches(pattern, number);
    }

    public static boolean isValidPasswd(String password) {
        if (password.isEmpty() || password.length() < 4 || password.length() > 10)
            return  false;
        return true;
    }

}
