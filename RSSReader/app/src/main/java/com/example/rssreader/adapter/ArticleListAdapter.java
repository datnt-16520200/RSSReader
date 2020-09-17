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

import com.example.rssreader.R;
import com.example.rssreader.activity.ArticleListActivity;
import com.example.rssreader.activity.DetailArticleActivity;
import com.example.rssreader.data.DBManager;
import com.example.rssreader.model.Article;
import com.example.rssreader.util.ConnectivityChecking;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Article> data;
    private Context context;
    private boolean hiddenReadLater; // value true: hidden "Read Later Option", value false: hidden "Delete Option"

    public ArticleListAdapter(Context context, List<Article> data, boolean hiddenReadLater) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.data = data;
        this.hiddenReadLater = hiddenReadLater;
    }

    @NonNull
    @Override
    public ArticleListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for item in the RecyclerView
        View v;
        v = layoutInflater.inflate(R.layout.article_item, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ArticleListAdapter.ViewHolder holder, final int position) {

        //set data for fields of Article item
        holder.tvArticleTitle.setText(data.get(position).getmTitle());
        holder.tvArticlePubDate.setText(data.get(position).getmPubDate());

        Picasso.with(context)
                .load(data.get(position).getmImgUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.default_logo)
                .into(holder.imgArticle);

        //Set event Click for Article item
        holder.cardArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("ArticleInfo", data.get(position).getmId()+" "+data.get(position).getmTitle()+" "+data.get(position).getmLink()+" "+data.get(position).getmImgUrl()+" "+data.get(position).getmPubDate());
                if (ConnectivityChecking.isConnected((Activity)context)){
                    //Go to webview to show article here
                    Intent intent = new Intent(context, DetailArticleActivity.class);

                    //packaged channel object to bundle
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",data.get(position).getmId());
                    bundle.putString("title",data.get(position).getmTitle().trim());
                    bundle.putString("link",data.get(position).getmLink().trim());
                    bundle.putString("imgUrl",data.get(position).getmImgUrl().trim());
                    bundle.putString("pubDate",data.get(position).getmPubDate().trim());

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

        //Set event Click for icon option
        holder.tvArticleOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a popup menu
                PopupMenu popup = new PopupMenu(context, holder.tvArticleOptions);

                //inflate menu from xml file
                popup.inflate(R.menu.article_item_options);

                //hidden menu item
                MenuItem hiddenItem;
                if (hiddenReadLater == true){
                    hiddenItem = popup.getMenu().findItem(R.id.article_option_read_later);
                }
                else {
                    hiddenItem = popup.getMenu().findItem(R.id.article_option_delete);
                }
                hiddenItem.setVisible(false);

                //add Click event
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.article_option_read_later){
                            //add article to database to read later
                            DBManager manager = new DBManager(context);
                            if (manager.addArticle(data.get(position))){
                                Toast.makeText(context, "Add article to Read Later successfully: "+data.get(position).getmTitle(), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Error")
                                        .setMessage("The Article was saved before!")
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
                        else if (id == R.id.article_option_delete){
                            //delete article from table articles
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Delete Article")
                                    .setMessage("Are you sure to delete this Article?")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //Delete article here
                                            DBManager manager = new DBManager(context);
                                            manager.deleteArticle(data.get(position).getmId());
                                            manager.close();

                                            //Delete article in adapter and notify
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

                popup.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgArticle;
        TextView tvArticleTitle, tvArticlePubDate, tvArticleOptions;
        CardView cardArticle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgArticle = itemView.findViewById(R.id.articleItemImage);
            tvArticleTitle = itemView.findViewById(R.id.articleItemTitle);
            tvArticlePubDate = itemView.findViewById(R.id.articleItemPubDate);
            tvArticleOptions = itemView.findViewById(R.id.articleItemOptions);
            cardArticle = itemView.findViewById(R.id.rootArticleItemCard);
        }
    }
}
