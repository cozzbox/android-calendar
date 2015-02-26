package com.cozzbox.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by haramaki on 2015/02/26.
 */
public class DateUtil {

    public static String getDateNow_YYYYMMDD() {
        final DateFormat df = new SimpleDateFormat("yyyyMMdd");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    public static String formatDateYYYYMMDD(Calendar calendar) {
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) +1;
        int d = calendar.get(Calendar.DAY_OF_MONTH);

        String mm = "";
        String dd = "";

        if (m < 10) mm = "0";
        if (d < 10) dd = "0";

        return y + mm + m + dd + d;
    }

}
