package com.example.me_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class SharedActivity extends AppCompatActivity {
    EditText username,password;
    RelativeLayout btn_login;
    String user,pass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        sharedPreferences = SharedActivity.this.getSharedPreferences("Mypref",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user = username.getText().toString();
                pass = password.getText().toString();

                if (user.isEmpty()||pass.isEmpty()){

                    username.setError("Please fill this fled");
                    password.setError("Please fill this field");
                }
                else {
                    editor.putString("USERNAME", user);
                    editor.putString("PASSWORD", pass);
                    editor.apply();

                    Intent intent = new Intent(SharedActivity.this,SharedOutputActivity.class);
                    startActivity(intent);


                }


            }
        });

    }
}