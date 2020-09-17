package com.example.rssreader.helper;

import android.util.Log;

import com.example.rssreader.model.Article;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

public class MySaxParser {
    public List<Article> xmlParser(InputStream is){
        List<Article> items = null;

        try {
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            MySaxHandler mySaxHandler = new MySaxHandler();
            xmlReader.setContentHandler(mySaxHandler);
            xmlReader.parse(new InputSource(is));
            items = mySaxHandler.getItems();
        }catch (Exception e){
            Log.d("loi","MySaxParser: "+e.toString());
        }
        return items;
    }
}
