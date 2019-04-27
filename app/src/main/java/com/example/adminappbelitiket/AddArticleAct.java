package com.example.adminappbelitiket;

import android.content.Intent;
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

import java.util.ArrayList;

public class AddArticleAct extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ArticleCard> list;
    ArticleAdapterCard adapterCard;
    Button btn_add_menu;

    DatabaseReference reference;

    int REQUEST_MENU = 404;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        recyclerView = findViewById(R.id.rvArticle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<ArticleCard>();

        btn_add_menu = findViewById(R.id.btn_add_article);
        btn_add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddArticleAct.this,InputArticleAct.class),REQUEST_MENU);
            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("Article");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ArticleCard card = dataSnapshot1.getValue(ArticleCard.class);
                    list.add(card);
                }
                adapterCard = new ArticleAdapterCard(list);
                recyclerView.setAdapter(adapterCard);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
