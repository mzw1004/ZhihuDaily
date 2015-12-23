package com.mzw.zhihudaily.view.custom;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mzw.zhihudaily.util.L;

/**
 * Created by M on 2015/12/14.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = L.makeLogTag(DividerItemDecoration.class);

    private HeaderProvider mHeaderProvider;
    private HeaderViewCache mHeaderViewCache;

    public DividerItemDecoration(HeaderProvider headerProvider) {
        mHeaderProvider = headerProvider;
        mHeaderViewCache = new HeaderViewCacheImpl(headerProvider);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (mHeaderProvider.hasHeader(position)) {
                RecyclerView.LayoutParams layoutParams =
                        (RecyclerView.LayoutParams) child.getLayoutParams();
                View header = mHeaderViewCache.getHeaderView(parent, position);
                int top = child.getTop() - layoutParams.topMargin - header.getHeight();
                c.save();
                c.translate(0, top);
                header.draw(c);
                c.restore();
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (mHeaderProvider.hasHeader(position)) {
            View header = mHeaderViewCache.getHeaderView(parent, position);
            outRect.set(0, header.getHeight(), 0, 0);
        }
    }
}
