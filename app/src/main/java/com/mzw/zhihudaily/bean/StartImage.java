package com.mzw.zhihudaily.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by M on 2015/11/9.
 */
public class StartImage {
    @SerializedName("text")
    public String author;

    @SerializedName("img")
    public String imageUrl;
}
