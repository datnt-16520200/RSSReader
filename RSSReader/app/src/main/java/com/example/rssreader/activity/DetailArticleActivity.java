package com.example.rssreader.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.rssreader.R;
import com.example.rssreader.model.Article;

public class DetailArticleActivity extends AppCompatActivity {

    WebView webView;
    ProgressDialog dialog;
    Article article;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = findViewById(R.id.detail_article_webview);
        dialog = new ProgressDialog(this);

        //Get data from ArticleListActivity
        Bundle bundle = getIntent().getExtras();
        article = new Article(bundle.getInt("id", -1),
                bundle.getString("title","No title"),
                bundle.getString("link","No link"),
                bundle.getString("imgUrl","No ImgUrl"),
                bundle.getString("pubDate","No PubDate"));

        Log.d("articleUrl",article.getmLink());

        //Config for webview
        webView.setWebViewClient(new WebViewClient(){

            //When WebView start, show dialog
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dialog.setMessage("Loading...");
                dialog.show();
            }

            //When Webview Commit Visible, dismiss dialog
            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                dialog.dismiss();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(article.getmLink());
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        webView.loadUrl(article.getmLink());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}