package com.example.rssreader.helper;

import android.util.Log;

import com.example.rssreader.model.Article;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class MySaxHandler extends DefaultHandler {
    ArrayList<Article> items;
    Article item;
    String content;
    boolean startItemtag = false;

    public MySaxHandler() {
        items = new ArrayList<>();
    }

    public ArrayList<Article> getItems(){
        return this.items;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equalsIgnoreCase("item")){
            item = new Article();
            startItemtag = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equalsIgnoreCase("item")){
            item.setmId(-1);
            items.add(item);
            startItemtag = false;
        }else if (startItemtag==true){
            if (qName.equalsIgnoreCase("title"))
                item.setmTitle(content);

            if (qName.equalsIgnoreCase("description")){
                //item.setDescription(content);
                try{
                    Document document = Jsoup.parse(content);
                    Element img = document.select("img").first();
                    item.setmImgUrl(img.absUrl("src"));
                }catch (Exception e){
                    Log.d("loi","MySaxHandler: "+e.toString());
                }
            }

            if (qName.equalsIgnoreCase("link"))
                item.setmLink(content);

            if (qName.equalsIgnoreCase("pubDate"))
                item.setmPubDate(content);

            if (qName.equalsIgnoreCase("img")){
                item.setmImgUrl(content);
                Log.d("imgtag",item.getmImgUrl());
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (startItemtag==true){
            content = new String(ch,start,length);
        }
    }
}

