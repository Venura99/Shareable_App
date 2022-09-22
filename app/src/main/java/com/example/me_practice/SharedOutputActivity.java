package com.example.me_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SharedOutputActivity extends AppCompatActivity {
    TextView view1,view2;
    RelativeLayout btn_show;
    String user,pass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_output);

        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        btn_show = findViewById(R.id.btn_show);

        sharedPreferences = SharedOutputActivity.this.getSharedPreferences("Mypref",MODE_PRIVATE);
        sharedPreferences.edit();

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user = sharedPreferences.getString("USERNAME","EMPTY");
                pass = sharedPreferences.getString("PASSWORD","EMPTY");

                view1.setText(user);
                view2.setText(pass);
            }
        });
    }
}