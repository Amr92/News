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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Adapters.RecyclerAdapter;
import com.example.newsapp.Model.Articles;
import com.example.newsapp.ui.NewsViewModel;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.et_query)
    EditText editText;
    Button button;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private RecyclerAdapter adapter;
    private final String API_KEY ="9e81e77519624e0b94f79848d4044360";
    private ProgressBar bar;
    private NewsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        bar = findViewById(R.id.progress_circular);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        final String country = getCountry();

        viewModel.getNewsArticles(country,API_KEY);

        viewModel.newsMutableLiveData.observe(this, new Observer<List<Articles>>() {
            @Override
            public void onChanged(List<Articles> articlesList) {
                bar.setVisibility(View.INVISIBLE);
                adapter = new RecyclerAdapter(articlesList,MainActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });

        button = findViewById(R.id.btnSearch);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editText.getText().toString().equals("")){
                    String search = editText.getText().toString();
                    viewModel.searchNewsArticles(search,API_KEY);
                }
                else{
                    viewModel.getNewsArticles(country,API_KEY);
                }
            }
        });

        if(isInternetAvailable()){
            onStart();
        }else{
            showCustomDialog();
        }
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

