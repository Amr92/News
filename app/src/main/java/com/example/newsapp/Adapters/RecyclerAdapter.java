package com.example.newsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.MainActivity;
import com.example.newsapp.Model.Articles;
import com.example.newsapp.Model.HeadLines;
import com.example.newsapp.R;
import com.example.newsapp.ui.Detailed;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.format.SimpleTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private List<Articles> articles;
    private Context context;

    public RecyclerAdapter(List<Articles> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec,parent,false);
        RecyclerViewHolder vH = new RecyclerViewHolder(view);
        return vH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {

        holder.tvTitle.setText(articles.get(position).getTitle());
        holder.tvSource.setText(articles.get(position).getSource().getName());
        holder.tvDate.setText(dateTime(articles.get(position).getPublishedAt()));
        Glide.with(context).load(articles.get(position).getUrlToImage()).into(holder.imgView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), Detailed.class);
                intent.putExtra("title",articles.get(position).getTitle());
                intent.putExtra("source",articles.get(position).getSource().getName());
                intent.putExtra("description",articles.get(position).getDescription());
                intent.putExtra("time",dateTime(articles.get(position).getPublishedAt()));
                intent.putExtra("imageUrl",articles.get(position).getUrlToImage());
                intent.putExtra("url",articles.get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle,tvSource,tvDate;
        ImageView imgView;
        CardView cardView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

           tvTitle = itemView.findViewById(R.id.tvTitle);
           tvSource = itemView.findViewById(R.id.tvSource);
           tvDate = itemView.findViewById(R.id.tvDate);
           imgView = itemView.findViewById(R.id.image);
           cardView = itemView.findViewById(R.id.card_view);

        }
    }

    public String dateTime(String t){
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        String time = null;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:",Locale.ENGLISH);
            Date date = simpleDateFormat.parse(t);
            time = prettyTime.format(date);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return time;
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
