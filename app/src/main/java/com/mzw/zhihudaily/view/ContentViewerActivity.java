package com.mzw.zhihudaily.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.mzw.zhihudaily.R;
import com.mzw.zhihudaily.bean.Content;
import com.mzw.zhihudaily.util.L;
import com.mzw.zhihudaily.view.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by M on 2015/12/13.
 */
public class ContentViewerActivity extends BaseActivity {
    private static final String TAG = L.makeLogTag(ContentViewerActivity.class);
    private static final String PARAM_ID = "id";

    public static void actionStart(Context context, long id) {
        Intent intent = new Intent(context, ContentViewerActivity.class);
        intent.putExtra(PARAM_ID, id);
        context.startActivity(intent);
    }

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.web_view)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_viewer);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        long id = getIntent().getLongExtra(PARAM_ID, -1L);
        L.d(TAG, "onCreate id: " + id);

        getContent(id);
    }

    private void getContent(long id) {
        mZhihuService.getContent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Content>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e(TAG, e, "");
                    }

                    @Override
                    public void onNext(Content content) {
                        String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_qa.auto.css\" />" + content.body;
                        mWebView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
