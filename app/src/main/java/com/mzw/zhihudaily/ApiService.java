package com.mzw.zhihudaily;

import com.mzw.zhihudaily.bean.StartImage;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by M on 2015/11/9.
 */
public interface ApiService {

    @GET("start-image/1080*1776")
    Call<StartImage> startImage();
}
