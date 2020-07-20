package com.example.newsapp.Interfaces;

import com.example.newsapp.Model.Articles;

import java.util.List;

public interface NewsView {

    void onGetArticles(List<Articles> articlesList);
    void onSearchArticles(List<Articles> arcList);
}
