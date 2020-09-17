package com.example.rssreader.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rssreader.R;
import com.example.rssreader.model.Channel;
import com.example.rssreader.network.LoadingArticleTask;

public class ArticleListActivity extends AppCompatActivity {

    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.articleListRecyclerView);

        //Get Bundle from ChannelListActivity
        Bundle bundle = getIntent().getExtras();
        Channel channel = new Channel(bundle.getInt("id"),
                bundle.getString("name"),
                bundle.getString("link"),
                bundle.getString("imgUrl"));

        this.setTitle(channel.getmName());

        LoadingArticleTask loadingArticleTask = new LoadingArticleTask(this,channel.getmLink());
        loadingArticleTask.execute();
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