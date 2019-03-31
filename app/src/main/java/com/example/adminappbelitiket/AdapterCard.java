package com.example.adminappbelitiket;


import android.annotation.SuppressLint;
import android.content.Context;
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
    Context context;
    DatabaseReference reference;

    public AdapterCard(ArrayList<Card> p, Context c) {
        context = c;
        myMenu = p;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.
                from(context).inflate(R.layout.home_card,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.xnama_menu.setText(myMenu.get(i).getNama_makanan());
        viewHolder.xharga.setText(myMenu.get(i).getHarga());
        Picasso.with(context).load(myMenu.get(i).getUrl_photo_menu()).centerCrop().fit().into(viewHolder.xphoto_menu);
    }

    @Override
    public int getItemCount() {
        return myMenu.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView xnama_menu, xharga;
        ImageView xphoto_menu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_menu = itemView.findViewById(R.id.tvJudul);
            xharga = itemView.findViewById(R.id.tvHarga);
            xphoto_menu = itemView.findViewById(R.id.pic_photo_makanan);

        }
    }
}
