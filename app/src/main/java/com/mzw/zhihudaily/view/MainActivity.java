package com.mzw.zhihudaily.view;

import android.os.Bundle;
import android.widget.TextView;

import com.mzw.zhihudaily.ApiClient;
import com.mzw.zhihudaily.R;
import com.mzw.zhihudaily.ApiService;
import com.mzw.zhihudaily.bean.StartImage;
import com.mzw.zhihudaily.util.L;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends BaseActivity {
    private static final String TAG = L.makeLogTag(MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
