package com.mzw.zhihudaily.api;

import com.squareup.okhttp.OkHttpClient;

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

    private ZhihuRetrofit() {
        mHttpClient = new OkHttpClient();
        mHttpClient.setReadTimeout(12, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://news-at.zhihu.com/api/4/")
                .client(mHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mService = retrofit.create(ZhihuService.class);
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
