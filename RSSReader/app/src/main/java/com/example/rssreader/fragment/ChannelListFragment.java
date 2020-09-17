package com.example.rssreader.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rssreader.R;
import com.example.rssreader.adapter.ChannelListAdapter;
import com.example.rssreader.data.DBManager;
import com.example.rssreader.model.Channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelListFragment extends Fragment {

    View v;
    RecyclerView recyclerView;
    List<Channel> lstChannel;
    ChannelListAdapter adapter;

    public ChannelListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_channel_list, container, false);
        recyclerView = v.findViewById(R.id.rcv_channel_list);

        //Create Adapter
        adapter = new ChannelListAdapter(getContext(),lstChannel);

        //Set layout and adapter for RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBManager manager = new DBManager(getContext());

        lstChannel = manager.getAllChannel();

        manager.close();

    }

    @Override
    public void onResume() {
        super.onResume();

        //clear list
        lstChannel.clear();

        //add all channel again
        DBManager manager = new DBManager(getContext());
        lstChannel.addAll(manager.getAllChannel());

        //notify for recyclerView
        adapter.notifyDataSetChanged();

        manager.close();

    }
}