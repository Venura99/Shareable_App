package com.example.me_practice.main_screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.me_practice.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText r_mail;
    RelativeLayout btn_reset;
    TextView resend_mail;
    String email;

    FirebaseAuth firebaseAuth;

    private String EmailPattern = "[a-zA-Z0-9.-_]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        r_mail = findViewById(R.id.r_email);
        btn_reset = findViewById(R.id.btn_reset);
        resend_mail = findViewById(R.id.resend_mail);
        firebaseAuth = FirebaseAuth.getInstance();

        resend_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = r_mail.getText().toString();
                validation();

            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = r_mail.getText().toString();

                validation();

            }
        });
    }

    private void validation() {
        if (!(email.isEmpty())){
            if (email.matches(EmailPattern)){
                //Send link
                SendMail(email);
                
            }else {
                r_mail.setError("Enter valid email address..!!");
            }
            
        }else {
            r_mail.setError("Please fill this field..!!");
        }
    }

    private void SendMail(String email) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ForgetPasswordActivity.this, "Send reset link successfully", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgetPasswordActivity.this, "Unsuccessfully..Try again..!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}