package com.mzw.zhihudaily.api;

import com.mzw.zhihudaily.bean.Content;
import com.mzw.zhihudaily.bean.Latest;
import com.mzw.zhihudaily.bean.StartImage;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by M on 2015/11/9.
 */
public interface ZhihuService {

    @GET("start-image/1080*1776")
    Observable<StartImage> startImage();

    @GET("news/latest")
    Observable<Latest> getLatest();

    @GET("news/{id}")
    Observable<Content> getContent(@Path("id") long id);
}
