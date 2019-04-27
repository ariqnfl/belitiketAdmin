package com.example.adminappbelitiket;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;

public class AdapterCard extends RecyclerView.Adapter<AdapterCard.ViewHolder> {
    ArrayList<Card> myMenu;

    public AdapterCard(ArrayList<Card> myMenu) {
        this.myMenu = myMenu;
    }

    @NonNull
    @Override
    public AdapterCard.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.home_card,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCard.ViewHolder viewHolder, int i) {
        viewHolder.bind(myMenu.get(i));
    }

    @Override
    public int getItemCount() {
        return myMenu == null ? 0 : myMenu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView xphoto_wisata;
        private String nama_wisata;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            xphoto_wisata = itemView.findViewById(R.id.ivWisata);
            itemView.setOnClickListener(this);
        }
        void bind(Card card){
            Picasso.with(itemView.getContext()).load(card.getUrl_thumbnail()).centerCrop().fit().into(xphoto_wisata);
            nama_wisata = card.getNama_wisata();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(itemView.getContext(),EditWisataAct.class);
            intent.putExtra("nama_wisata",nama_wisata);
            itemView.getContext().startActivity(intent);
        }
    }
}
