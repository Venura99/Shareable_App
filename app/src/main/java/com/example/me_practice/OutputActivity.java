package com.example.me_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OutputActivity extends AppCompatActivity {
    TextView view1,view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);

        Bundle bundle = getIntent().getExtras();

        String user = bundle.getString("USERNAME","NO USER");
        String pass = bundle.getString("PASSWORD","NO PASSWORD");

        view1.setText(user);
        view2.setText(pass);

    }
}