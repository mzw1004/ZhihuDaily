package com.mzw.zhihudaily.view.custom;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by M on 2015/12/19.
 */
public interface HeaderProvider {

    View getHeader(ViewGroup parent, int position);

    boolean hasHeader(int position);
}
