package com.exomatik.irfanrz.exomatik.AuthenticationUser.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.exomatik.irfanrz.exomatik.Activity.MainActivity;
import com.exomatik.irfanrz.exomatik.AuthenticationUser.register.registerUser;
import com.exomatik.irfanrz.exomatik.R;

public class loginUser extends AppCompatActivity implements LoginContract.View {
    private EditText userEmail, userPass;
    private Button userLogin, userRegister;
    private LoginPresenter mLoginPresenter;
    private Button showPassword;
    private ImageView back;
    private int show = 0;
    public static int activity;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        userEmail = (EditText) findViewById(R.id.userEmail);
        userPass = (EditText) findViewById(R.id.userPass);
        userLogin = (Button) findViewById(R.id.userLogin);
        userRegister = (Button) findViewById(R.id.userRegister);
        showPassword = (Button) findViewById(R.id.show_password);
        back = (ImageView) findViewById(R.id.loginBack);

        mLoginPresenter = new LoginPresenter(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginUser.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show == 1) {
                    show = 0;
                    showPassword.setBackgroundResource(R.drawable.ic_dont_show);
                    userPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else if (show == 0) {
                    show = 1;
                    showPassword.setBackgroundResource(R.drawable.ic_show);
                    userPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(loginUser.this);
                progressDialog.setMessage("Mohon Tunggu...");
                progressDialog.setTitle("Proses");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String emailId = userEmail.getText().toString();
                String password = userPass.getText().toString();

                mLoginPresenter.login(loginUser.this, emailId, password);
            }
        });

        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginUser.this, registerUser.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onLoginSuccess(String message) {
        Toast.makeText(loginUser.this, message, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        Intent intent = new Intent(loginUser.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailure(String message) {
        progressDialog.dismiss();
        Toast.makeText(loginUser.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(loginUser.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
