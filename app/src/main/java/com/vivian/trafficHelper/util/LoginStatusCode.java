package com.vivian.trafficHelper.util;

public enum LoginStatusCode {
    SUCCESS("登录成功"), NETWORKERROR("网络错误"), VALIDATIONERROR("手机号不合法"),ERRORLOG("用户名或密码不正确");

    private String errorMsg;
    LoginStatusCode(String msg) {
        errorMsg = msg;
    }
    public String getErrorMsg(){
        return errorMsg;
    }
}
