package com.example.rssreader.model;

public class Channel {
    private int mId;
    private String mName;
    private String mLink;
    private String mImgUrl;

    public Channel(int mId, String mName, String mLink, String mImgUrl) {
        this.mId = mId;
        this.mName = mName;
        this.mLink = mLink;
        this.mImgUrl = mImgUrl;
    }

    public Channel() {
    }

    public Channel(String mName, String mLink, String mImgUrl) {
        this.mName = mName;
        this.mLink = mLink;
        this.mImgUrl = mImgUrl;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
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

    @Override
    public String toString() {
        return "Channel{" +
                "mName='" + mName + '\'' +
                '}';
    }
}
