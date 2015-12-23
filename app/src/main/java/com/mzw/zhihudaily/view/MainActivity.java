package com.mzw.zhihudaily.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mzw.zhihudaily.R;
import com.mzw.zhihudaily.bean.NewsList;
import com.mzw.zhihudaily.bean.Story;
import com.mzw.zhihudaily.util.DateHelper;
import com.mzw.zhihudaily.util.L;
import com.mzw.zhihudaily.view.adapter.MainAdapter;
import com.mzw.zhihudaily.view.base.BaseActivity;
import com.mzw.zhihudaily.view.custom.DividerItemDecoration;
import com.mzw.zhihudaily.view.custom.EndlessOnScrollListener;

import java.util.ArrayList;
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

    private MainAdapter mMainAdapter;

    private int mDaysBefore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addOnScrollListener(getOnScrollListener());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mMainAdapter = new MainAdapter(new ArrayList<Story>());
        mMainAdapter.setOnItemClickListener(getOnItemClickListener());
        mRecyclerView.setAdapter(mMainAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(mMainAdapter);
        mRecyclerView.addItemDecoration(itemDecoration);

        loadLatest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                loadLatest();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadLatest() {
        mZhihuService.getLatest()
                .subscribeOn(Schedulers.io())
                .map(new Func1<NewsList, List<Story>>() {
                    @Override
                    public List<Story> call(NewsList newsList) {
                        L.d(TAG, "Thread: " + Thread.currentThread().getName());
                        String date = newsList.date;
                        mMainAdapter.addDate(date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8));
                        return newsList.stories;
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
                        if (mMainAdapter != null) {
                            mMainAdapter.updateStory(stories);
                        }
                        mDaysBefore = 0;
                    }
                });
    }

    private void loadBefore(int days) {
        mZhihuService.getBefore(DateHelper.getDatebeforeToday(days))
                .subscribeOn(Schedulers.io())
                .map(new Func1<NewsList, List<Story>>() {
                    @Override
                    public List<Story> call(NewsList newsList) {
                        String date = newsList.date;
                        mMainAdapter.addDate(date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8));
                        return newsList.stories;
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
                        if (mMainAdapter != null) {
                            mMainAdapter.addStory(stories);
                            mDaysBefore++;
                        }
                    }
                });
    }

    private void gotoContent(long id) {
        ContentViewerActivity.actionStart(this, id);
    }

    private RecyclerView.OnScrollListener getOnScrollListener() {
        return new EndlessOnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mMainAdapter.setViewIdle(true);
                } else {
                    mMainAdapter.setViewIdle(false);
                }
            }

            @Override
            public void onLoadMore() {
                loadBefore(mDaysBefore);
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
