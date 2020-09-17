package com.example.rssreader.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rssreader.R;
import com.example.rssreader.adapter.ArticleListAdapter;
import com.example.rssreader.data.DBManager;
import com.example.rssreader.model.Article;

import java.util.List;

public class ReadLaterFragment extends Fragment {

    View v;
    RecyclerView recyclerView;
    List<Article> listArticle;

    public ReadLaterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_read_later, container, false);
        recyclerView = v.findViewById(R.id.rcv_article_list);

        //Create Adapter
        ArticleListAdapter adapter = new ArticleListAdapter(getContext(),listArticle,true);

        //Set layout and adapter for RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBManager manager = new DBManager(getContext());

        listArticle = manager.getAllArticle();

        manager.close();
    }
}