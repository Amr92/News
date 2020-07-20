package com.example.newsapp.Interfaces;

import com.example.newsapp.Model.HeadLines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<HeadLines> getHeadLines(@Query("country") String country,
                                 @Query("apiKey") String apiKey);

    @GET("everything")
    Call<HeadLines> searchNewsArticles(@Query("q") String country,
                                       @Query("apiKey") String apiKey);

}
