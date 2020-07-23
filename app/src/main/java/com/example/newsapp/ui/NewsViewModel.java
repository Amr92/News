package com.example.newsapp.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.Classes.RetrofitClient;
import com.example.newsapp.Model.Articles;
import com.example.newsapp.Model.HeadLines;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    private List<Articles> articles = new ArrayList<>();
    public MutableLiveData<List<Articles>> newsMutableLiveData = new MutableLiveData<>();
    
    public void getNewsArticles(String country,String api_Key){

        Call<HeadLines> call = RetrofitClient.getInstance().getApi().getHeadLines(country,api_Key);
        call.enqueue(new Callback<HeadLines>() {
            @Override
            public void onResponse(Call<HeadLines> call, Response<HeadLines> response) {
                if(response.isSuccessful() && response.body().getArticles() != null) {
                    articles.addAll(response.body().getArticles());
                    newsMutableLiveData.setValue(articles);
                }
            }

            @Override
            public void onFailure(Call<HeadLines> call, Throwable t) {

            }
        });
    }

    public void searchNewsArticles(String query,String api_Key){

        Call<HeadLines> call = RetrofitClient.getInstance().getApi().searchNewsArticles(query,api_Key);
        call.enqueue(new Callback<HeadLines>() {
            @Override
            public void onResponse(Call<HeadLines> call, Response<HeadLines> response) {
                if(response.isSuccessful() && response.body().getArticles() != null) {
                    articles.clear();
                    articles.addAll(response.body().getArticles());
                    newsMutableLiveData.setValue(articles);
                }
            }

            @Override
            public void onFailure(Call<HeadLines> call, Throwable t) {

            }
        });
    }
}
