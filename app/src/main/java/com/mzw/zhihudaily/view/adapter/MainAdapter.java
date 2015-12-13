package com.mzw.zhihudaily.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mzw.zhihudaily.App;
import com.mzw.zhihudaily.R;
import com.mzw.zhihudaily.bean.Story;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M on 2015/12/8.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private ArrayList<Story> mStoryList;
    private boolean mViewIdle = true;

    public MainAdapter(List<Story> stories) {
        mStoryList = new ArrayList<>(stories);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Story story = mStoryList.get(position);
        holder.textView.setText(story.title);
        RequestCreator requestCreator = Picasso.with(App.getContext()).load(story.images.get(0));
        if (isViewIdle()) {
            requestCreator.into(holder.imageView);
        } else {
            requestCreator.networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mStoryList.size();
    }

    public boolean isViewIdle() {
        return mViewIdle;
    }

    public void setViewIdle(boolean viewIdle) {
        if (viewIdle != mViewIdle) {
            mViewIdle = viewIdle;
            notifyDataSetChanged();
        }
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MainViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_main);
            textView = (TextView) itemView.findViewById(R.id.tv_main);
        }
    }
}
