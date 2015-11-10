package com.mzw.zhihudaily;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by M on 2015/11/9.
 */
public class ApiClient {

    private static Retrofit sRetrofit;
    private static ApiService sApiService;

    static {
        sRetrofit = new Retrofit.Builder()
                .baseUrl("http://news-at.zhihu.com/api/4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sApiService = sRetrofit.create(ApiService.class);
    }

    public static Retrofit getRetrofit() {
        return sRetrofit;
    }

    public static ApiService getApiService() {
        return sApiService;
    }
}
