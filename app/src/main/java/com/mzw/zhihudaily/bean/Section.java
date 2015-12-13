package com.mzw.zhihudaily.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by M on 2015/12/13.
 */
public class Section {

    @SerializedName("thumbnail")
    public String thumbnail;

    @SerializedName("id")
    public long id;

    @SerializedName("name")
    public String name;
}
