package com.example.rssreader.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rssreader.activity.ArticleListActivity;
import com.example.rssreader.adapter.ArticleListAdapter;
import com.example.rssreader.helper.MySaxParser;
import com.example.rssreader.model.Article;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;

public class LoadingArticleTask extends AsyncTask<Void,Void,Void> {
    Context context;
    String link;
    ArrayList<Article> items;
    String temp;
    ProgressDialog dialog;

    public LoadingArticleTask(Context context, String link) {
        this.context = context;
        this.link = link;
        items = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            items = (ArrayList<Article>) new MySaxParser().xmlParser(is);

        }catch (Exception e){
            Log.d("Loi","NewsTask: "+e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ((ArticleListActivity)context).recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ArticleListAdapter newsAdapter = new ArticleListAdapter(context,items,false);
        ((ArticleListActivity)context).recyclerView.setAdapter(newsAdapter);


        dialog.dismiss();
    }
}

