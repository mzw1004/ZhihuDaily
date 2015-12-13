package com.mzw.zhihudaily.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by M on 2015/12/13.
 */
public class Content {

    @SerializedName("body")
    public String body;

    @SerializedName("image_source")
    public String imageSource;

    @SerializedName("title")
    public String title;

    @SerializedName("image")
    public String image;

    @SerializedName("share_url")
    public String shareUrl;

    @SerializedName("js")
    public List<String> js;

    @SerializedName("ga_prefix")
    public String gaPrefix;

    @SerializedName("section")
    public Section section;

    @SerializedName("type")
    public int type;

    @SerializedName("id")
    public long id;

    @SerializedName("css")
    public List<String> css;
}
