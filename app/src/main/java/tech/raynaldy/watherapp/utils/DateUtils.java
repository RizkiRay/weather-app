package tech.raynaldy.watherapp.utils;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/5/21.
 */

public class DateUtils {
    private static final String TAG = "DateUtils";

    public static String humanDateFormatter(String date) {
        SimpleDateFormat myDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String out = "";
        try {
            Date inDate = inputDateFormat.parse(date);
            out = myDateFormat.format(inDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return out;
    }
    public static String getStringDate(Date date) {
        SimpleDateFormat myDateFormat = new SimpleDateFormat("EEE d MMM", Locale.US);
        String out = "";
        return myDateFormat.format(date);
    }

}
