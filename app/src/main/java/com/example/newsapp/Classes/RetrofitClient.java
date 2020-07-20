package com.example.newsapp.Classes;

import com.example.newsapp.Interfaces.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_URL ="https://newsapi.org/v2/";
    private static RetrofitClient apiClient;
    private Retrofit retrofit;

    private RetrofitClient(){

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(apiClient == null){
            apiClient = new RetrofitClient();
        }
        return apiClient;
    }

    public ApiInterface getApi(){

        return retrofit.create(ApiInterface.class);
    }

}
