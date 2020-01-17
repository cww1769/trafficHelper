package com.vivian.trafficHelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vivian.trafficHelper.R;
import com.vivian.trafficHelper.util.LoginStatusCode;
import com.vivian.trafficHelper.util.ServerHelper;
import com.vivian.trafficHelper.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_mobile) EditText _mobileText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    return;
                }
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        _signupButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("创建中...");
        progressDialog.show();

        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();

        final LoginStatusCode statusCode = ServerHelper.signup(mobile, password);
        switch (statusCode) {
            case SUCCESS:
                Log.i(TAG, "successfully signup, jumped to main activity");
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                onSignupSuccess();
                                progressDialog.dismiss();
                            }
                        }, 1000);
                break;
            case NETWORKERROR:
            case ERRORLOG:
                Log.i(TAG, "failed signup");
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                onSignupFailed(statusCode);
                                progressDialog.dismiss();
                            }
                        }, 1000);
                break;
        }
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        MyApplication.setIsLogged(true);
        setResult(RESULT_OK, null);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }

    public void onSignupFailed(LoginStatusCode statusCode) {
        Toast.makeText(getBaseContext(), statusCode.getErrorMsg(), Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (!Utils.isValidPhoneNumber(mobile)) {
            _mobileText.setError(Utils.ERRPHONE);
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (!Utils.isValidPasswd(password)) {
            _passwordText.setError(Utils.ERRPASS);
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (!(reEnterPassword.equals(password)) || !Utils.isValidPasswd(reEnterPassword)) {
            _reEnterPasswordText.setError("两次输入的密码不一致");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}