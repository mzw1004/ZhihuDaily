package com.mzw.zhihudaily.view.custom;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by M on 2015/12/23.
 */
public class HeaderViewCacheImpl implements HeaderViewCache {

    private SparseArray<View> mHeaderViewCache = new SparseArray<>();
    private HeaderProvider mHeaderProvider;

    public HeaderViewCacheImpl(HeaderProvider headerProvider) {
        mHeaderProvider = headerProvider;
    }

    @Override
    public View getHeaderView(ViewGroup parent, int position) {
        View header = mHeaderViewCache.get(position);
        if (header == null) {
            header = mHeaderProvider.getHeader(parent, position);
            setMeasureHeader(parent, header);
            mHeaderViewCache.put(position, header);
        }
        return header;
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
