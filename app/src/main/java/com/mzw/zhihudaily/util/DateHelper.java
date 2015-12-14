package com.mzw.zhihudaily.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by M on 2015/12/14.
 */
public class DateHelper {

    public static String getDatebeforeToday(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, - days);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = calendar.getTime();
        return dateFormat.format(date);
    }
}
