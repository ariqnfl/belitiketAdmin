package com.example.adminappbelitiket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HomeAct extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Card> list;
    AdapterCard adapterCard;
    Button btn_add_menu;


    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    StorageReference storage;

    int REQUEST_MENU = 404;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.rvHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Card>();

        btn_add_menu = findViewById(R.id.btn_add_menu);
        btn_add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(HomeAct.this,InputAct.class),REQUEST_MENU);
            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Card card = dataSnapshot1.getValue(Card.class);
                    list.add(card);
                }
                adapterCard = new AdapterCard(list);
                recyclerView.setAdapter(adapterCard);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
