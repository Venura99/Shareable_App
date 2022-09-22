package com.example.me_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    RelativeLayout btn_login;
    String user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user = username.getText().toString();
                pass = password.getText().toString();

                if(user.isEmpty() || pass.isEmpty()){
                    ShowError();
                }
                else{
                    ShowOutput();
                }
            }
        });
    }

    private void ShowOutput() {
        Bundle bundle = new Bundle();

        bundle.putString("USERNAME",user);
        bundle.putString("PASSWORD",pass);

        Intent intent = new Intent(MainActivity.this,OutputActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

        Toast.makeText(MainActivity.this,"Successfully Login",Toast.LENGTH_SHORT).show();
    }

    private void ShowError() {
        username.setError("Please fill this field");
        password.setError("Please fill this field");
    }
}