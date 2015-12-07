package com.mzw.zhihudaily.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by M on 2015/12/7.
 */
public class Story {
    @SerializedName("images")
    public List<String> images;

    @SerializedName("type")
    public int type;

    @SerializedName("id")
    public long id;

    @SerializedName("ga_prefix")
    public String gaPrefix;

    @SerializedName("title")
    public String title;
}
