package com.example.newsapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Detailed extends AppCompatActivity {

    @BindView(R.id.tvTitle_2)
    TextView tvTitle;
    @BindView(R.id.tvSource_2)
    TextView tvSource;
    @BindView(R.id.tvDate_2)
    TextView tvDate;
    @BindView(R.id.tvDesc_2)
    TextView tvDesc;
    WebView webView;
    @BindView(R.id.imageView)
    ImageView image;

    private ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);

        webView = findViewById(R.id.web_view);

        loader = findViewById(R.id.web_view_loader);
        loader.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        String time = intent.getStringExtra("time");
        String desc = intent.getStringExtra("description");
        String imageUrl = intent.getStringExtra("imageUrl");
        String url = intent.getStringExtra("url");

        tvTitle.setText(title);
        tvDesc.setText(desc);
        tvSource.setText(source);
        tvDate.setText(time);

        Glide.with(this).load(imageUrl).into(image);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        if(webView.isShown()){
            loader.setVisibility(View.INVISIBLE);
        }
    }
}
