package com.example.adminappbelitiket;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArticleAdapterCard extends RecyclerView.Adapter<ArticleAdapterCard.ViewHolder> {
    ArrayList<ArticleCard> myArticle;

    public ArticleAdapterCard(ArrayList<ArticleCard> myArticle) {
        this.myArticle = myArticle;
    }

    @NonNull
    @Override
    public ArticleAdapterCard.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.article_card,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapterCard.ViewHolder viewHolder, int i) {
        viewHolder.bind(myArticle.get(i));
    }

    @Override
    public int getItemCount() {
        return myArticle == null ? 0 : myArticle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivArticle;
        private String nama_artikel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivArticle = itemView.findViewById(R.id.ivArticle);
            itemView.setOnClickListener(this);
        }
        void bind(ArticleCard articleCard){
            Picasso.with(itemView.getContext()).load(articleCard.getUrl_photo_artikel()).centerCrop().fit().into(ivArticle);
            nama_artikel = articleCard.getNama_artikel();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(itemView.getContext(),EditArticleAct.class);
            intent.putExtra("nama_artikel",nama_artikel);
            itemView.getContext().startActivity(intent);
        }
    }
}
