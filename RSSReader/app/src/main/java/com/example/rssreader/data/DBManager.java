package com.example.rssreader.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rssreader.model.Article;
import com.example.rssreader.model.Channel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rss_manager";
    private static final String TABLE_CHANNEL = "channels";
    private static final String TABLE_ARTICLE = "articles";
    private static int VERSION = 1;

    private String SQLCreateChannelTable = "CREATE TABLE "+TABLE_CHANNEL+" ("+
            "id INTEGER PRIMARY KEY, "+
            "name TEXT, "+
            "link TEXT, "+
            "imgurl TEXT)";

    private String SQLCreateArticleTable = "CREATE TABLE "+TABLE_ARTICLE+" ("+
            "id INTEGER PRIMARY KEY, "+
            "title TEXT, "+
            "link TEXT, "+
            "imgurl TEXT, "+
            "pubdate TEXT)";

    public DBManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create table channels and articles
        sqLiteDatabase.execSQL(SQLCreateChannelTable);
        sqLiteDatabase.execSQL(SQLCreateArticleTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Channel Table task
    //Add a channel to channels table
    public boolean addChannel(Channel channel){
        SQLiteDatabase db = this.getWritableDatabase();

        //if link has exist then do not add Channel and return false
        String q = "SELECT * FROM "+TABLE_CHANNEL+" WHERE TRIM(link) = '"+channel.getmLink().trim()+"'";
        Cursor cursor = db.rawQuery(q,null);

        if (cursor.moveToFirst()){
            return false;
        }

        //put properties of channel to values to add to table;
        ContentValues values = new ContentValues();
        values.put("name", channel.getmName().trim());
        values.put("link", channel.getmLink().trim());
        values.put("imgurl", channel.getmImgUrl().trim());

        //add channel to table
        db.insert(TABLE_CHANNEL,null, values);
        db.close();
        return true;
    }

    //Get all Channel
    public List<Channel> getAllChannel(){
        List<Channel> listChannel = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CHANNEL;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Channel channel = new Channel();

                channel.setmId(cursor.getInt(0));
                channel.setmName(cursor.getString(1).trim());
                channel.setmLink(cursor.getString(2).trim());
                channel.setmImgUrl(cursor.getString(3).trim());

                listChannel.add(channel);
            } while (cursor.moveToNext());
        }
        db.close();
        return listChannel;
    }

    //Update a channel
    public int updateChannel(Channel channel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", channel.getmName().trim());
        values.put("link", channel.getmLink().trim());
        values.put("imgurl", channel.getmImgUrl().trim());

        int num = db.update(TABLE_CHANNEL,values,"id = ?",new String[]{String.valueOf(channel.getmId())});
        db.close();
        return num;
    }

    //Delete Channel
    public int deleteChannel(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int num = db.delete(TABLE_CHANNEL,"id = ?",new String[]{String.valueOf(id)});
        db.close();
        return num;
    }


    //Article Table task
    //Add a article to articles table
    public boolean addArticle(Article article){
        SQLiteDatabase db = this.getWritableDatabase();

        //if link has exist then do not add Article and return false
        String q = "SELECT * FROM "+TABLE_ARTICLE+" WHERE TRIM(link) = '"+article.getmLink().trim()+"'";
        Cursor cursor = db.rawQuery(q,null);

        if (cursor.moveToFirst()){
            return false;
        }

        //put properties of article to values to add to table;
        ContentValues values = new ContentValues();
        values.put("title", article.getmTitle().trim());
        values.put("link", article.getmLink().trim());
        values.put("imgurl", article.getmImgUrl().trim());
        values.put("pubdate", article.getmPubDate().trim());

        //add article to table
        db.insert(TABLE_ARTICLE,null, values);
        db.close();
        return true;
    }

    //Get all article
    public List<Article> getAllArticle(){
        List<Article> listArticle = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_ARTICLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Article article = new Article();

                article.setmId(cursor.getInt(0));
                article.setmTitle(cursor.getString(1).trim());
                article.setmLink(cursor.getString(2).trim());
                article.setmImgUrl(cursor.getString(3).trim());
                article.setmPubDate(cursor.getString(4).trim());

                listArticle.add(article);

            } while (cursor.moveToNext());
        }
        db.close();
        return listArticle;
    }

    //Delete Article
    public int deleteArticle(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int num = db.delete(TABLE_ARTICLE,"id = ?",new String[]{String.valueOf(id)});
        db.close();
        return num;
    }
}
