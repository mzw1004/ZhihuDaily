package com.mzw.zhihudaily.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.mzw.zhihudaily.R;
import com.mzw.zhihudaily.bean.Latest;
import com.mzw.zhihudaily.bean.Story;
import com.mzw.zhihudaily.util.L;
import com.mzw.zhihudaily.view.base.BaseActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class MainActivity extends BaseActivity {
    private static final String TAG = L.makeLogTag(MainActivity.class);

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mZhihuService.getLatest()
                .map(new Func1<Latest, List<Story>>() {
                    @Override
                    public List<Story> call(Latest latest) {
                        return latest.stories;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Story>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Story> stories) {
                        Toast.makeText(MainActivity.this, stories.size(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
