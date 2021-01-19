package com.example.covidnews.Objects;

import android.graphics.Bitmap;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class NewsItem {
    private Bitmap mAvt;
    private String mTitle;
    private Date mTime;
    private String mAuthor;
    private String mDes;
    private String mLink;

    public NewsItem(Bitmap mAvt, String mTitle, Date mTime, String mAuthor, String mDes, String mLink) {
        this.mAvt = mAvt;
        this.mTitle = mTitle;
        this.mTime = mTime;
        this.mAuthor = mAuthor;
        this.mDes = mDes;
        this.mLink = mLink;
    }

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public Bitmap getmAvt() {
        return mAvt;
    }

    public void setmAvt(Bitmap mAvt) {
        this.mAvt = mAvt;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmDes() {
        return mDes;
    }

    public void setmDes(String mDes) {
        this.mDes = mDes;
    }
}
