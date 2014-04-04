package de.atomfrede.android.mensa.upb;

import java.util.Calendar;

/**
 * Created by fred on 16.02.14.
 */
public class CacheValueUtil {

    public static int getWeekOfYear() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getYear() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }

    public static int getCacheValue() {
        int weekOfYear = getWeekOfYear();
        int year = getYear();

        return year + weekOfYear;
    }
}
