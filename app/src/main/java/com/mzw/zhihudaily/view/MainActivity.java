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
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
                .subscribeOn(Schedulers.io())
                .map(new Func1<Latest, List<Story>>() {
                    @Override
                    public List<Story> call(Latest latest) {
                        return latest.stories;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Story>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Story> stories) {
                        Toast.makeText(MainActivity.this, stories.get(0).title, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
