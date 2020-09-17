package com.example.rssreader.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rssreader.MainActivity;
import com.example.rssreader.R;
import com.example.rssreader.activity.ArticleListActivity;
import com.example.rssreader.activity.EditChannelActivity;
import com.example.rssreader.data.DBManager;
import com.example.rssreader.model.Channel;
import com.example.rssreader.util.ConnectivityChecking;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Channel> data;
    private Context context;

    public ChannelListAdapter(Context context, List<Channel> data){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.data = data;
    }

    @NonNull
    @Override
    public ChannelListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for item in the RecyclerView
        View v;
        v = layoutInflater.inflate(R.layout.channel_item,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChannelListAdapter.ViewHolder holder, final int position) {

        //set data for fields of channel item
        holder.tvChannelName.setText(data.get(position).getmName());

        Picasso.with(context)
                .load(data.get(position).getmImgUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.drawable.default_logo)
                .into(holder.imgChannel);


        //set even click for channel item
        holder.cardChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectivityChecking.isConnected((Activity)context)){
                    //Go to list article activity
                    Intent intent = new Intent(context, ArticleListActivity.class);

                    //packaged channel object to bundle
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",data.get(position).getmId());
                    bundle.putString("name",data.get(position).getmName().trim());
                    bundle.putString("link",data.get(position).getmLink().trim());
                    bundle.putString("imgUrl",data.get(position).getmImgUrl().trim());

                    //put bundle to intent
                    intent.putExtras(bundle);

                    //start ArticleListActivity
                    context.startActivity(intent);
                }
                else {
                    ConnectivityChecking.showWifiDialog(context);
                }

            }
        });

        //set even Click for icon option
        holder.tvChannelOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a popup menu
                PopupMenu popup = new PopupMenu(context, holder.tvChannelOptions);

                //inflate menu from xml file
                popup.inflate(R.menu.channel_item_options);

                //add Click event
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //get Id of a option
                        int id = item.getItemId();

                        if (id == R.id.channel_option_edit){
                            //Go to activity edit channel here
                            Intent intent = new Intent(context, EditChannelActivity.class);

                            //packaged channel object to bundle
                            Bundle bundle = new Bundle();
                            bundle.putInt("id",data.get(position).getmId());
                            bundle.putString("name",data.get(position).getmName().trim());
                            bundle.putString("link",data.get(position).getmLink().trim());
                            bundle.putString("imgUrl",data.get(position).getmImgUrl().trim());

                            //put bundle to intent
                            intent.putExtras(bundle);

                            //start ArticleListActivity
                            context.startActivity(intent);
                        }
                        else if (id == R.id.channel_option_delete){
                            //Delete channel here
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Delete Channel")
                                    .setMessage("Are you sure to delete '"+data.get(position).getmName()+"' channel?")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //Delete Channel from database
                                            DBManager manager = new DBManager(context);
                                            manager.deleteChannel(data.get(position).getmId());
                                            manager.close();

                                            //Delete channel in adapter and notify
                                            data.remove(position);
                                            notifyDataSetChanged();

                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                        return false;
                    }
                });

                //show menu options
                popup.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgChannel;
        TextView tvChannelName,tvChannelOptions;
        CardView cardChannel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgChannel = itemView.findViewById(R.id.channelItemImage);
            tvChannelName = itemView.findViewById(R.id.channelItemTitle);
            cardChannel = itemView.findViewById(R.id.rootChannelItemCard);
            tvChannelOptions = itemView.findViewById(R.id.channelItemOptions);

        }
    }


}
