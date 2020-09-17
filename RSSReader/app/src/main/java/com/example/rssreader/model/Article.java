package com.example.rssreader.model;

public class Article {
    private int mId;
    private String mTitle;
    private String mLink;
    private String mImgUrl;
    private String mPubDate;

    public Article() {
    }

    public Article(String mTitle, String mLink, String mImgUrl, String mPubDate) {
        this.mTitle = mTitle;
        this.mLink = mLink;
        this.mImgUrl = mImgUrl;
        this.mPubDate = mPubDate;
    }

    public Article(int mId, String mTitle, String mLink, String mImgUrl, String mPubDate) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mLink = mLink;
        this.mImgUrl = mImgUrl;
        this.mPubDate = mPubDate;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public String getmImgUrl() {
        return mImgUrl;
    }

    public void setmImgUrl(String mImgUrl) {
        this.mImgUrl = mImgUrl;
    }

    public String getmPubDate() {
        return mPubDate;
    }

    public void setmPubDate(String mPubDate) {
        this.mPubDate = mPubDate;
    }
}
