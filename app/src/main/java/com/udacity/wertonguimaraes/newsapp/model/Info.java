package com.udacity.wertonguimaraes.newsapp.model;


public class Info {
    private String mTitle;
    private String mSection;
    private String mWebPublicationDate;
    private String mAuthor;
    private String mWebUrl;

    public Info(String title, String section, String webPublicationDate, String author, String webUrl) {
        mTitle = title;
        mSection = section;
        mWebPublicationDate = webPublicationDate;
        mAuthor = author;
        mWebUrl = webUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getWebPublicationDate() {
        return mWebPublicationDate;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getWebUrl() {
        return mWebUrl;
    }
}