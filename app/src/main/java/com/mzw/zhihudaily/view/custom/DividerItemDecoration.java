package com.mzw.zhihudaily.view.custom;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mzw.zhihudaily.util.L;

/**
 * Created by M on 2015/12/14.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = L.makeLogTag(DividerItemDecoration.class);

    private static final int mDividerSize = 50;
    private Paint mPaint;
    private HeaderProvider mHeaderProvider;

    public DividerItemDecoration(HeaderProvider headerProvider) {
        mHeaderProvider = headerProvider;
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int recyclerViewTop = parent.getPaddingTop();
        int recyclerViewBottom = parent.getHeight() - parent.getPaddingBottom();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (mHeaderProvider.hasHeader(position)) {
                RecyclerView.LayoutParams layoutParams =
                        (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = Math.max(recyclerViewTop, child.getTop() - layoutParams.topMargin - mDividerSize);
                int bottom = Math.min(recyclerViewBottom, child.getTop() - layoutParams.topMargin);
                View header = mHeaderProvider.getHeader(parent, position);
                setMeasureHeader(parent, header);
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
            outRect.set(0, mDividerSize, 0, 0);
        }
    }

    private void setMeasureHeader(ViewGroup parent, View header) {
        if (header.getLayoutParams() == null) {
            header.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        int widthSpec;
        int heightSpec;

        widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                parent.getPaddingLeft() + parent.getPaddingRight(), header.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                parent.getPaddingTop() + parent.getPaddingBottom(), header.getLayoutParams().height);
        header.measure(childWidth, childHeight);
        header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
    }
}
