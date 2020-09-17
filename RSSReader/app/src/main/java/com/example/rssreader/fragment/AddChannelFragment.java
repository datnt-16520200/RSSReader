package com.example.rssreader.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rssreader.R;
import com.example.rssreader.data.DBManager;
import com.example.rssreader.model.Channel;

public class AddChannelFragment extends Fragment {

    View v;
    EditText edtChannelName, edtChannelLink;
    Button btnAddChannel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_add_channel, container, false);
        edtChannelName = v.findViewById(R.id.add_channel_name);
        edtChannelLink = v.findViewById(R.id.add_channel_link);
        btnAddChannel = v.findViewById(R.id.add_channel_btnAdd);

        btnAddChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String channelName = edtChannelName.getText().toString();
                String channelLink = edtChannelLink.getText().toString();

                Channel channel = new Channel(channelName, channelLink, "default");

                DBManager manager = new DBManager(getContext());

                if (manager.addChannel(channel)){
                    Toast.makeText(getContext(), "Add Channel Successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Error")
                            .setMessage("The Link has exist!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                manager.close();
            }
        });

        return v;
    }
}