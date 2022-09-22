package com.example.me_practice.firebase_login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.me_practice.R;
import com.example.me_practice.SharedActivity;
import com.example.me_practice.main_screens.ForgetPasswordActivity;
import com.example.me_practice.main_screens.ListViewActivity;
import com.example.me_practice.main_screens.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView sign_up, forget_p;
    EditText email,password;
    RelativeLayout btn_login;
    Snackbar snackbar;
    ConstraintLayout constraintLayout;

    FirebaseAuth firebaseAuth;

    private String EmailPattern = "[a-zA-Z0-9.-_]+@[a-z]+\\.+[a-z]+";
    private String PasswordPattern = "[a-zA-Z0-9\\\\!\\\\@\\\\#\\\\$]{8,24}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email2);
        password = findViewById(R.id.password2);
        btn_login = findViewById(R.id.btn_login2);
        constraintLayout = findViewById(R.id.constraints);
        sign_up = findViewById(R.id.sign_up);
        forget_p = findViewById(R.id.forget_p);
        firebaseAuth = FirebaseAuth.getInstance();

        forget_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });


    }

    private void validate() {
        String user,pass;

        user = email.getText().toString();
        pass = password.getText().toString();

        if (!(user.isEmpty())){
            {
                if (!(pass.isEmpty())){

                    if (user.matches(EmailPattern)){

                        if (pass.matches(PasswordPattern)){
                            logUser(user,pass);
                        }
                        else {
                            password.setError("character limit is 8-24..!!");
                        }

                    }else {
                        email.setError("Enter valid email address..!!");
                    }

                }else{
                    password.setError("Please fill this field..!!");
                }
            }

        }else {
            email.setError("Please fill this field..!!");
        }
    }

    private void logUser(String user,String pass) {

        //snack bar
        snackbar = Snackbar.make(constraintLayout,"Login Please Wait..!!",Snackbar.LENGTH_LONG).
                setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
        snackbar.setActionTextColor(Color.CYAN);
        snackbar.show();

        //firebase
        firebaseAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login successfully..!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, ListViewActivity.class));
                }
                else {
                    Toast.makeText(LoginActivity.this, "User not valid..!!", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Unsuccessful..!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}