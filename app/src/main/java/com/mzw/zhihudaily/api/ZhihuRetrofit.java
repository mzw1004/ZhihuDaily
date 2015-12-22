package com.mzw.zhihudaily.api;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.mzw.zhihudaily.App;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by M on 2015/11/9.
 */
public class ZhihuRetrofit {

    private ZhihuService mService;
    private OkHttpClient mHttpClient;

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;

    private ZhihuRetrofit() {
        setUpHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://news-at.zhihu.com/api/4/")
                .client(mHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mService = retrofit.create(ZhihuService.class);
    }

    private void setUpHttpClient() {
        mHttpClient = new OkHttpClient();
        mHttpClient.networkInterceptors().add(new StethoInterceptor());
        mHttpClient.setReadTimeout(12, TimeUnit.SECONDS);
        File baseDir = App.getContext().getCacheDir();
        if (baseDir != null) {
            File cacheFile = new File(baseDir, "HttpResponseCache");
            mHttpClient.setCache(new Cache(cacheFile, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE));
        }
    }

    private static class RetrofitHolder {
        private static final ZhihuRetrofit sRetrofit = new ZhihuRetrofit();
    }

    public static ZhihuRetrofit getInstance() {
        return RetrofitHolder.sRetrofit;
    }

    public ZhihuService getService() {
        return mService;
    }

    public OkHttpClient getHttpClient() {
        return mHttpClient;
    }
}
