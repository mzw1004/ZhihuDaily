package com.mzw.zhihudaily.view.base;

import android.support.v7.app.AppCompatActivity;

import com.mzw.zhihudaily.api.ZhihuRetrofit;
import com.mzw.zhihudaily.api.ZhihuService;

/**
 * Created by M on 2015/11/10.
 */
public class BaseActivity extends AppCompatActivity {
    protected ZhihuService mZhihuService = ZhihuRetrofit.getInstance().getService();
}
