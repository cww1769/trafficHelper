package com.vivian.trafficHelper;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vivian.housekeeper.R;
import com.vivian.trafficHelper.data.UserInfo;
import com.vivian.trafficHelper.util.LoginStatusCode;
import com.vivian.trafficHelper.util.ServerHelper;
import com.vivian.trafficHelper.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_phone) EditText _phoneNumber;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;
    //TODO: need sms support
    @BindView(R.id.link_forgotPasswd) TextView _forgotPassLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //if there has user info, if yes, login and jump to main activity; if no, nothing to do.
        List<UserInfo> users = MyApplication.getInstance().getUsers();
        if (users != null) {
            Log.i(TAG, "has saved user info, login automatically and jump to main activity");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } else {
            _loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!validate()) {
                        return;
                    }
                    login();
                }
            });

            _signupLink.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Start the Signup activity
                    Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                    startActivityForResult(intent, REQUEST_SIGNUP);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            });
        }
    }

    public void login() {
        Log.d(TAG, "Login");
        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("登录中...");
        progressDialog.show();

        String phoneNumber = _phoneNumber.getText().toString();
        String password = _passwordText.getText().toString();
        final LoginStatusCode statusCode = ServerHelper.login(phoneNumber, password);
        switch (statusCode) {
            case SUCCESS:
                Log.i(TAG, "successfully login, jumped to main activity");
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                onLoginSuccess();
                                progressDialog.dismiss();
                            }
                        }, 1000);
                break;
            case NETWORKERROR:
            case ERRORLOG:
                Log.i(TAG, "failed login");
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                onLoginFailed(statusCode);
                                progressDialog.dismiss();
                            }
                        }, 1000);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        MyApplication.setIsLogged(true);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }

    public void onLoginFailed(LoginStatusCode statusCode) {
        Toast.makeText(getBaseContext(), statusCode.getErrorMsg(), Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String phoneNumber = _phoneNumber.getText().toString();
        String password = _passwordText.getText().toString();

        if (!Utils.isValidPhoneNumber(phoneNumber)) {
            _phoneNumber.setError(Utils.ERRPHONE);
            valid = false;
        } else {
            _phoneNumber.setError(null);
        }

        if (!Utils.isValidPasswd(password)) {
            _passwordText.setError("请输入4到10位的字符或数字");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }
}
