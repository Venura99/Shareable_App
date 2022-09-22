package com.example.me_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.me_practice.firebase_login_signup.SignupActivity;

public class WelcomeActivity extends AppCompatActivity {
    ImageView welcome_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcome_img = findViewById(R.id.welcome_img);
        Animation animation = AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.zoom_in);
        welcome_img.startAnimation(animation);

        Intent intent = new Intent(WelcomeActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}