package com.mzw.zhihudaily.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by M on 2015/12/7.
 */
public class NewsList {

    @SerializedName("date")
    public String date;

    @SerializedName("stories")
    public List<Story> stories;

    @SerializedName("top_stories")
    public List<TopStory> topStories;
}
