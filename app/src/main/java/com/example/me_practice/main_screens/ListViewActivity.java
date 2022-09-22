package com.example.me_practice.main_screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.me_practice.Adapter.PostADAPTER;
import com.example.me_practice.DTO.PostDTO;
import com.example.me_practice.R;
import com.example.me_practice.SharedActivity;
import com.example.me_practice.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.View;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    RecyclerView rv_list;
    ArrayList<PostDTO> mData;
    PostADAPTER postAdapter;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        rv_list = findViewById(R.id.rv_list);
        firestore = FirebaseFirestore.getInstance();
        loadData();
    }

    private void loadData() {

        firestore.collection("Post").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    mData = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        String name = documentSnapshot.getData().get("Name").toString();
                        String time = documentSnapshot.getData().get("Time").toString();
                        String description = documentSnapshot.getData().get("Description").toString();
                        mData.add(new PostDTO(name,time,description,R.drawable.user_p,R.drawable.prof));
                    }
                    postAdapter = new PostADAPTER(mData,ListViewActivity.this,ListViewActivity.this::onClickListner);
                    rv_list.setAdapter(postAdapter);
                    rv_list.setLayoutManager(new LinearLayoutManager(ListViewActivity.this,LinearLayoutManager.VERTICAL,false));
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

//    private void loadData() {
//        mData = new ArrayList<>();
//        mData.add(new PostDTO("Kusal Perera", "10 Minutes ago","fefeferfgjkreger egjrgerjgre regjerg",R.drawable.prof,R.drawable.user_p));
//        mData.add(new PostDTO("Kusal Perera", "10 Minutes ago","fefeferfgjkreger egjrgerjgre regjerg",R.drawable.prof,R.drawable.user_p));
//        mData.add(new PostDTO("Kusal Perera", "10 Minutes ago","fefeferfgjkreger egjrgerjgre regjerg",R.drawable.prof,R.drawable.user_p));
//        mData.add(new PostDTO("Kusal Perera", "10 Minutes ago","fefeferfgjkreger egjrgerjgre regjerg",R.drawable.prof,R.drawable.user_p));
//        mData.add(new PostDTO("Kusal Perera", "10 Minutes ago","fefeferfgjkreger egjrgerjgre regjerg",R.drawable.prof,R.drawable.user_p));
//
//        postAdapter = new PostADAPTER(mData,ListViewActivity.this,ListViewActivity.this::onClickListner);
//        rv_list.setAdapter(postAdapter);
//        rv_list.setLayoutManager(new LinearLayoutManager(this));
//
//    }

    private void onClickListner(int i) {
        Intent intent = new Intent(ListViewActivity.this, SharedActivity.class);
        startActivity(intent);
    }


}