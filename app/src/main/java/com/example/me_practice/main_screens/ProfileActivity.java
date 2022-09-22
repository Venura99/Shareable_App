package com.example.me_practice.main_screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.me_practice.R;
import com.example.me_practice.firebase_login_signup.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
    TextView user_name,email_address;
    RelativeLayout btn_update, logout_btn;
    String Uid;
    ImageView edit_user,edit_email,edit_pass;

    AlertDialog.Builder builder;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user_name = findViewById(R.id.user_name);
        email_address = findViewById(R.id.email_address);
        edit_user = findViewById(R.id.edit_user);
        edit_email = findViewById(R.id.edit_email);
        edit_pass = findViewById(R.id.edit_pass);
        btn_update = findViewById(R.id.btn_update);
        logout_btn = findViewById(R.id.logout_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        Uid = firebaseAuth.getUid();

        LoadUserDetails();

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutUser();
            }
        });

        edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Update");
                builder.setMessage("Enter new username..");
                EditText new_user_name = new EditText(ProfileActivity.this);
                new_user_name.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(new_user_name);

                builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //update username
                        String user = new_user_name.getText().toString();
                        firestore.collection("User").document(Uid).update("UserName",user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ProfileActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                                // use this function for show update details
                                LoadUserDetails();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //dialog box dismiss

                    }
                });

                builder.show();
            }
        });

        edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReAuthenticateAlert();
            }
        });

        edit_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(ProfileActivity.this,ForgetPasswordActivity.class));
            }
        });

    }

    private void ReAuthenticateAlert() {
        builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Re Authenticate");
        builder.setMessage("Enter your password..");
        EditText new_email = new EditText(ProfileActivity.this);
        new_email.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(new_email);

        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //update username
                String user = new_email.getText().toString();
                ReAuthenticate(user);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dialog box dismiss

            }
        });

        builder.show();

    }

    private void ReAuthenticate(String user) {
        String email = email_address.getText().toString();
        AuthCredential authCredential = EmailAuthProvider.getCredential(email,user);
        firebaseAuth.getCurrentUser().reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                UpdateEmail(email);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "Credential not match..!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void UpdateEmail(String email) {

        builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Update");
        builder.setMessage("Enter new email..");
        EditText new_user_name = new EditText(ProfileActivity.this);
        new_user_name.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(new_user_name);

        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //update username
                String new_user = new_user_name.getText().toString();
                firebaseAuth.getCurrentUser().updateEmail(new_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        firestore.collection("User").document(Uid).update("Email",new_user);
                        LoadUserDetails();
                        Toast.makeText(ProfileActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dialog box dismiss

            }
        });

        builder.show();

    }

    private void LogoutUser() {
        firebaseAuth.signOut();
        startActivity(new Intent(ProfileActivity.this, SignupActivity.class));
        finish();
    }

    private void LoadUserDetails() {

        DocumentReference documentReference = firestore.collection("User").document(Uid);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    String user = (String) doc.getData().get("UserName");
                    String email = (String) doc.getData().get("Email");

                    user_name.setText(user);
                    email_address.setText(email);
                }
                else {

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}