package com.mzw.zhihudaily.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mzw.zhihudaily.App;
import com.mzw.zhihudaily.R;
import com.mzw.zhihudaily.bean.Story;
import com.mzw.zhihudaily.view.custom.HeaderProvider;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M on 2015/12/8.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>
        implements HeaderProvider {

    private ArrayList<Story> mStoryList;
    private SparseArray<String> mHeaderMap = new SparseArray<>();
    private Context mContext;
    private boolean mViewIdle = true;

    public MainAdapter(Context context, List<Story> stories) {
        mContext = context;
        mStoryList = new ArrayList<>(stories);
    }

    public OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    @Override
    public View getHeader(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_main, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.tv_main_header);
        textView.setText(mHeaderMap.get(position));
        return view;
    }

    @Override
    public boolean hasHeader(int position) {
        int index = mHeaderMap.indexOfKey(position);
        return index >= 0;
    }

    public interface OnItemClickListener {
        void onItemClicked(View view, int position);
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

    public Story getStory(int position) {
        return mStoryList.get(position);
    }

    public void addStory(List<Story> stories) {
        mStoryList.addAll(stories);
        notifyItemRangeChanged(mStoryList.size() - stories.size(), stories.size());
    }

    public void updateStory(List<Story> stories) {
        mStoryList.clear();
        mStoryList.addAll(stories);
        notifyDataSetChanged();
    }

    public void addDate(String date) {
        mHeaderMap.put(mStoryList.size(), date);
    }

    public class MainViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        public MainViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_main);
            textView = (TextView) itemView.findViewById(R.id.tv_main);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(v, getAdapterPosition());
            }
        }
    }
}
