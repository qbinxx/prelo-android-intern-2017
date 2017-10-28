package com.example.sukmaapp.preloandroiddeveloperchallenge;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private String username, password;

    private Context context;
    private EditText etUsername, etPassword;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();
        getSupportActionBar().setTitle("Login");

        initComponents();
        setBtnLogin();
    }

    private void initComponents(){
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    private void setBtnLogin(){
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if(username.trim().length() <= 0){
                    Toast.makeText(context, "Username can't be empty", Toast.LENGTH_SHORT).show();
                }else if(username.trim().length() < 4){
                    Toast.makeText(context, "Username must contain at least 4 characters", Toast.LENGTH_SHORT).show();
                }else if(password.trim().length() <= 0){
                    Toast.makeText(context, "Password can't be empty", Toast.LENGTH_SHORT).show();
                }else if(password.trim().length() < 6){
                    Toast.makeText(context, "password must contain at least 6 characters", Toast.LENGTH_SHORT).show();
                }else{
                    loginProcess(username,password);
                }
            }
        });
    }

    private void loginProcess(String username, String password){
        //ToDo: Do Login Request Service
    }
}
