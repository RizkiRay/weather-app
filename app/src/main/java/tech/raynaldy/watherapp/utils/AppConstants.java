package tech.raynaldy.watherapp.utils;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */

public final class AppConstants {
    public static final String DB_NAME = "weather.db";
    public static final String PREF_NAME = "weather_pref";

    public static final long NULL_INDEX = -1L;
    public static final int API_STATUS_CODE_LOCAL_ERROR = 1301;

    private AppConstants() {
        //tidak dapat diinstansiasi secara public
    }
}
