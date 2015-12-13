package com.mzw.zhihudaily.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mzw.zhihudaily.R;
import com.mzw.zhihudaily.bean.Latest;
import com.mzw.zhihudaily.bean.Story;
import com.mzw.zhihudaily.util.L;
import com.mzw.zhihudaily.view.adapter.MainAdapter;
import com.mzw.zhihudaily.view.base.BaseActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private static final String TAG = L.makeLogTag(MainActivity.class);

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    MainAdapter mMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addOnScrollListener(getOnScrollListener());

        loadStories();
    }

    private void loadStories() {
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
                        mMainAdapter = new MainAdapter(stories);
                        mMainAdapter.setOnItemClickListener(getOnItemClickListener());
                        mRecyclerView.setAdapter(mMainAdapter);
                    }
                });
    }

    private void gotoContent(long id) {
        ContentViewerActivity.actionStart(this, id);
    }

    private RecyclerView.OnScrollListener getOnScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mMainAdapter.setViewIdle(true);
                } else {
                    mMainAdapter.setViewIdle(false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        };
    }

    private MainAdapter.OnItemClickListener getOnItemClickListener() {
        return new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Story story = mMainAdapter.getStory(position);
                gotoContent(story.id);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
