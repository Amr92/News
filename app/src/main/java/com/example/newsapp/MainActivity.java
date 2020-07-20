package com.example.newsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Adapters.RecyclerAdapter;
import com.example.newsapp.Classes.NewsPresenter;
import com.example.newsapp.Interfaces.NewsView;
import com.example.newsapp.Model.Articles;
import com.example.newsapp.Model.HeadLines;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements NewsView {

    @BindView(R.id.et_query)
    EditText editText;
    Button button;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private RecyclerAdapter adapter;
    private final String API_KEY ="9e81e77519624e0b94f79848d4044360";
    private ProgressBar bar;
    private NewsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bar = findViewById(R.id.progress_circular);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        presenter = new NewsPresenter(this);

        final String country = getCountry();
        presenter.getNewsArticles(country,API_KEY);

        button = findViewById(R.id.btnSearch);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editText.getText().toString().equals("")){
                    String search = editText.getText().toString();
                    presenter.searchNewsArticles(search,API_KEY);
                }
                else{
                    presenter.getNewsArticles(country,API_KEY);
                }
            }
        });

        if(isInternetAvailable()){
            onStart();
        }else{
            showCustomDialog();
        }
    }

    @Override
    public void onGetArticles(List<Articles> articlesList) {

        bar.setVisibility(View.INVISIBLE);
        adapter = new RecyclerAdapter(articlesList,MainActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchArticles(List<Articles> arcList) {

        bar.setVisibility(View.INVISIBLE);
        adapter = new RecyclerAdapter(arcList,MainActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Please connect to the Internet to proceed..")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).create().show();
    }

}

