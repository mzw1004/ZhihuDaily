package com.mzw.zhihudaily.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by M on 2015/12/7.
 */
public class TopStory {
    @SerializedName("image")
    String images;

    @SerializedName("type")
    int type;

    @SerializedName("id")
    long id;

    @SerializedName("ga_prefix")
    String gaPrefix;

    @SerializedName("title")
    String title;
}
