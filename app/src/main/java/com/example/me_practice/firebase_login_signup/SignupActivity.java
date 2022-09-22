package com.example.me_practice.firebase_login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.me_practice.R;
import com.example.me_practice.main_screens.ListViewActivity;
import com.example.me_practice.main_screens.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    TextView sign_in;
    EditText user,email,pass;
    RelativeLayout btn_reg;


    private String EmailPattern = "[a-zA-Z0-9.-_]+@[a-z]+\\.+[a-z]+";
    private String PasswordPattern = "[a-zA-Z0-9\\\\!\\\\@\\\\#\\\\$]{8,24}";

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        user = findViewById(R.id.username);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        btn_reg = findViewById(R.id.btn_register);
        sign_in = findViewById(R.id.sign_in);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //open in current user

        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(SignupActivity.this, ListViewActivity.class));
        }

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {
        String s_user,s_pass,s_mail;

       s_user = user.getText().toString();
       s_mail = email.getText().toString();
       s_pass = pass.getText().toString();

       if (!(s_user.isEmpty())){

           if(!(s_mail.isEmpty())) {

               if (!(s_pass.isEmpty())){


                    if (s_mail.matches(EmailPattern)) {


                        if (s_pass.matches(PasswordPattern)) {

                            RegisterUser(s_mail,s_pass,s_user);

                        }
                        else {
                            pass.setError("character limit is 8-24..!!");
                        }
                   }
                    else {
                        email.setError("Enter valid email address..!!");
                    }
               }
               else{
                   pass.setError("Please fill this field..!!");
               }
           }
           else{
           email.setError("Please fill this field..!!");
           }

       }
       else {
           user.setError("Please fill this field..!!");
       }

    }

    private void RegisterUser(String s_mail, String s_pass, String s_user) {

        firebaseAuth.createUserWithEmailAndPassword(s_mail,s_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Toast.makeText(SignupActivity.this, "successfully register..!!", Toast.LENGTH_SHORT).show();
                    //save data
                    String userId = firebaseAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("User").document(userId);
                    Map<String, Object> users = new HashMap<>();
                    users.put("Email", s_mail);
                    users.put("UserName",s_user);
                    documentReference.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(SignupActivity.this, "successfully register..!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupActivity.this, "Register unsuccessfully..!!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else {
                    Toast.makeText(SignupActivity.this, "Error..!!User exsist", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupActivity.this, "unsuccessfully..!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}