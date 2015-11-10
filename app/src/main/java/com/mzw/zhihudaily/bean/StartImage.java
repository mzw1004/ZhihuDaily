package com.mzw.zhihudaily.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by M on 2015/11/9.
 */
public class StartImage {

    @SerializedName("text")
    String mText;

    @SerializedName("img")
    String mImgUrl;

    public StartImage(String text, String imgUrl) {
        mText = text;
        mImgUrl = imgUrl;
    }

    public String getText() {
        return mText;
    }

    public String getImgUrl() {
        return mImgUrl;
    }
}
